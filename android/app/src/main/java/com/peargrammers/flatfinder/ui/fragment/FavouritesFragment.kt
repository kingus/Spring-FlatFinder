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
import com.peargrammers.flatfinder.ui.viewmodel.UserOfferViewModel
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.android.synthetic.main.favourites_fragment.*

class FavouritesFragment : Fragment(R.layout.favourites_fragment), OfferAdapter.OnItemClickListener{
    private val TAG = FavouritesFragment::class.qualifiedName
    lateinit var viewModel: UserOfferViewModel
    lateinit var offersAdapter: OfferAdapter
    lateinit var userPreferencesImpl: UserPreferencesImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).userOfferViewModel
        setupRecyclerView()

        userPreferencesImpl = UserPreferencesImpl(requireContext())

        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { token ->

            if (token != null) {
                viewModel.getUserOffers(token)
            }

        })

        viewModel.userOffers.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { offerResponse ->
                        offersAdapter.differ.submitList(offerResponse)

                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured $message")
                    }
                }
                is Resource.Loading -> {
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

    override fun onItemClick(offerId: Int?) {
        Log.d(TAG, "onItemClick")
    }

}