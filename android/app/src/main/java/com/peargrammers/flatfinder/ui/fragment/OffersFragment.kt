package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.adapter.OfferAdapter
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.android.synthetic.main.home_fragment.rvOffers
import kotlinx.android.synthetic.main.offers_fragment.*

class OffersFragment : Fragment(R.layout.offers_fragment) {
    lateinit var viewModel: OfferViewModel
    lateinit var offersAdapter: OfferAdapter
    private val TAG = OffersFragment::class.qualifiedName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated")

        viewModel = (activity as HomeActivity).viewModel
        setupRecyclerView()

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
        offersAdapter = OfferAdapter()
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

}