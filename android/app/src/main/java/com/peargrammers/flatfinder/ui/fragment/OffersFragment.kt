package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.adapter.OfferAdapter
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.android.synthetic.main.home_fragment.rvOffers
import kotlinx.android.synthetic.main.offers_fragment.*

class OffersFragment : Fragment(R.layout.offers_fragment), OfferAdapter.OnItemClickListener {
    lateinit var viewModel: OfferViewModel
    lateinit var offersAdapter: OfferAdapter
    private val TAG = OffersFragment::class.qualifiedName
    lateinit var userPreferencesImpl: UserPreferencesImpl
    lateinit var token: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, activity.toString())

        Log.d(TAG, "onViewCreated")



        viewModel = (activity as HomeActivity).offerViewModel
        setupRecyclerView()

        userPreferencesImpl = UserPreferencesImpl(requireContext())

        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { token ->

            if (token != null) {
                this.token = token
            }
        })

        viewModel.offers.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { offerResponse ->
                        offersAdapter.differ.submitList(offerResponse)

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
        offersAdapter = OfferAdapter(this)
        rvOffers.apply {
            adapter = offersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onItemClick(offerId: Int?) {
        if (offerId != null) {
            viewModel.sendEmail(token, offerId)
        }
        Log.d(TAG, "onItemClick")
        Log.d("offerId", offerId.toString())
    }

}