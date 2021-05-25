package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.adapter.OfferAdapter
import com.peargrammers.flatfinder.adapter.UserOfferAdapter
import com.peargrammers.flatfinder.dao.UserOffersRequest
import com.peargrammers.flatfinder.databinding.OffersFragmentBinding
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.model.UserOffer
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import com.peargrammers.flatfinder.utils.Resource

class OffersFragment : Fragment(R.layout.offers_fragment), OfferAdapter.OnItemClickListener {
    lateinit var viewModel: OfferViewModel
    lateinit var offerViewModel: OfferViewModel
    lateinit var offerAdapter: OfferAdapter
    private val TAG = OffersFragment::class.qualifiedName
    lateinit var userPreferencesImpl: UserPreferencesImpl
    lateinit var token: String

    private var _binding: OffersFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OffersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, activity.toString())

        Log.d(TAG, "onViewCreated")

        viewModel = (activity as HomeActivity).offerViewModel
        offerViewModel = (activity as HomeActivity).offerViewModel
        setupRecyclerView()

        userPreferencesImpl = UserPreferencesImpl(requireContext())

        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { token ->

            if (token != null) {
                this.token = token
                viewModel.getOffers(token)
            }
        })


        viewModel.offers.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { offerResponse ->
                        offerAdapter.items = offerResponse
                        offerAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun setupRecyclerView() {
        offerAdapter = OfferAdapter(listOf(), this)
        binding.rvOffers.apply {
            adapter = offerAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        binding.rvOffers.setItemViewCacheSize(1000)
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onItemClick(offer: UserOffer, view: View?) {

        when (view?.id) {

            R.id.emailImageView -> {

                viewModel.sendEmail(token, offer.id)
                Log.d(TAG, "emailImageView")

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
                    R.id.action_offersFragment_to_offerFragment,
                    bundle
                )

            }
        }
        Log.d(TAG, "onItemClick")
    }

}