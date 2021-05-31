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
import com.peargrammers.flatfinder.adapter.FavOfferAdapter
import com.peargrammers.flatfinder.adapter.OfferAdapter
import com.peargrammers.flatfinder.dao.AddUserOfferRequest
import com.peargrammers.flatfinder.databinding.FavouritesFragmentBinding
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.model.UserOffer
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.dialog.ChangeNoteDialog
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import com.peargrammers.flatfinder.ui.viewmodel.UserOfferViewModel

class FavouritesFragment : Fragment(R.layout.favourites_fragment),
    FavOfferAdapter.OnItemClickListener {
    private val TAG = FavouritesFragment::class.qualifiedName
    lateinit var userOfferViewModel: UserOfferViewModel
    lateinit var offerViewModel: OfferViewModel
    lateinit var offersAdapter: FavOfferAdapter
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
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })

        binding.searchView.setOnCloseListener {
            getAllUserOffers()
        }

        getAllUserOffers()

        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { auth ->

            if (auth != null) {
                token = auth
                offerViewModel.getFavouriteOffers(token)
            }

        })

    }

    private fun setupRecyclerView() {
        offersAdapter = FavOfferAdapter(listOf(), this)
        binding.rvOffers.apply {
            adapter = offersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        binding.rvOffers.setItemViewCacheSize(1000)
        offersAdapter.notifyDataSetChanged()
    }


    private fun searchUserOffers(query: String) {
        val searchQuery = "%$query%"
        Log.d("QUERY ", query)
        offerViewModel.searchUserOffer(searchQuery).observe(viewLifecycleOwner, Observer { offers ->
            offers.map {
                Log.d("OFFERS ", it.title)
            }
            offersAdapter.items = offers
            offersAdapter.notifyDataSetChanged()
        })
    }

    private fun getAllUserOffers(): Boolean {
        offerViewModel.getSavedOffers().observe(viewLifecycleOwner, Observer { offers ->
            offersAdapter.items = offers
            offersAdapter.notifyDataSetChanged()
        })
        return true
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
                val addUserOfferRequest = AddUserOfferRequest(offer.id, "")

                if (!offer.isFavourite) {

                    offerViewModel.deleteOffer(offer)
                    offerViewModel.deleteUserOffer(token, offer.id)

                } else {

                    offerViewModel.saveOffer(offer)
                    offerViewModel.addUserOffers(token, addUserOfferRequest)

                }

            }

            R.id.editImageView -> {
                showChangeNoteDialog(offer)
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

    private fun showChangeNoteDialog(offer: UserOffer) {
        val dialog = ChangeNoteDialog(offer, token)
        activity?.supportFragmentManager?.let { dialog.show(it, "OfferNoteDialog") }
    }

}