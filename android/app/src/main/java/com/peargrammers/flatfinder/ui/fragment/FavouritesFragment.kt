package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.adapter.UserOfferAdapter
import com.peargrammers.flatfinder.dao.UserOffersRequest
import com.peargrammers.flatfinder.databinding.FavouritesFragmentBinding
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.model.UserOffer
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import com.peargrammers.flatfinder.ui.viewmodel.UserOfferViewModel

class FavouritesFragment : Fragment(R.layout.favourites_fragment),
    UserOfferAdapter.OnItemClickListener {
    private val TAG = FavouritesFragment::class.qualifiedName
    lateinit var userOfferViewModel: UserOfferViewModel
    lateinit var offerViewModel: OfferViewModel
    lateinit var offersAdapter: UserOfferAdapter
    lateinit var userPreferencesImpl: UserPreferencesImpl
    lateinit var token: String

    private var _binding: FavouritesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavouritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userOfferViewModel = (activity as HomeActivity).userOfferViewModel
        offerViewModel = (activity as HomeActivity).offerViewModel
        userPreferencesImpl = UserPreferencesImpl(requireContext())
        setupRecyclerView()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchUserOffers(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
        binding.searchView.setOnCloseListener {
            binding.searchView.clearFocus()
            getAllUserOffers()
        }

        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { token ->

            if (token != null) {
                offerViewModel.getFavouriteOffers(token)
            }

        })

        getAllUserOffers()

        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { auth ->

            if (auth != null) {
                token = auth
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.search_menu, menu)
//
//        val search = menu.findItem(R.menu.search_menu)
//        val searchView = search.actionView as SearchView
//        searchView.isSubmitButtonEnabled = true
    }


    private fun setupRecyclerView() {
        offersAdapter = UserOfferAdapter(this)
        binding.rvOffers.apply {
            adapter = offersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onItemClick(offer: UserOffer, view: View?) {
        Log.d("onItemClick", view.toString())

        when (view?.id) {

            R.id.emailImageView -> {

                Log.d(TAG, "emailImageView")
                offerViewModel.sendEmail(token, offer.id)

            }

            R.id.heartImageView -> {

                Log.d(TAG, "heartImageView")
                val userOffersRequest = UserOffersRequest(offer.id, "")

                if (!offer.isFavourite) {

                    offerViewModel.deleteOffer(offer)
                    offerViewModel.deleteUserOffer(token, offer.id)

                } else {

                    offerViewModel.saveOffer(offer)
                    offerViewModel.updateUserOffers(token, userOffersRequest)

                }

            }
            else -> {

                val bundle = Bundle().apply {
                    putSerializable("offer", offer)
                }

                findNavController().navigate(
                    R.id.action_favouritesFragment_to_offerFragment,
                    bundle
                )

            }
        }
    }

    private fun searchUserOffers(query: String) {
        val searchQuery = "%$query%"
        Log.d("QUERY ", query)
        offerViewModel.searchUserOffer(searchQuery).observe(viewLifecycleOwner, Observer { offers ->
            offers.map {
                Log.d("OFFERS ", it.title.toString())
            }
            offersAdapter.differ.submitList(offers)
        })
    }

    private fun getAllUserOffers(): Boolean {
        offerViewModel.getSavedOffers().observe(viewLifecycleOwner, Observer { offers ->
            offersAdapter.differ.submitList(offers)
        })
        return true
    }
}