package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.adapter.UserOfferAdapter
import com.peargrammers.flatfinder.dao.UserOffersRequest
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.model.UserOffer
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import com.peargrammers.flatfinder.ui.viewmodel.UserOfferViewModel
import kotlinx.android.synthetic.main.favourites_fragment.*

class FavouritesFragment : Fragment(R.layout.favourites_fragment),
    UserOfferAdapter.OnItemClickListener {
    private val TAG = FavouritesFragment::class.qualifiedName
    lateinit var userOfferViewModel: UserOfferViewModel
    lateinit var offerViewModel: OfferViewModel
    lateinit var offersAdapter: UserOfferAdapter
    lateinit var userPreferencesImpl: UserPreferencesImpl
    lateinit var token: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userOfferViewModel = (activity as HomeActivity).userOfferViewModel
        offerViewModel = (activity as HomeActivity).offerViewModel
        userPreferencesImpl = UserPreferencesImpl(requireContext())
        setupRecyclerView()

        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { token ->

            if (token != null) {
                offerViewModel.getFavouriteOffers(token)
            }

        })

        offerViewModel.getSavedOffers().observe(viewLifecycleOwner, Observer { offers ->
            offersAdapter.differ.submitList(offers)
        })


        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { auth ->

            if (auth != null) {
                token = auth
            }

        })
    }


    private fun setupRecyclerView() {
        offersAdapter = UserOfferAdapter(this)
        rvOffers.apply {
            adapter = offersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onItemClick(offer: UserOffer, view: View?) {

        when (view?.id) {

            R.id.emailImageView -> {

                offerViewModel.sendEmail(token, offer.id)
                Log.d(TAG, "emailImageView")

            }

            R.id.heartImageView -> {
                Log.d(TAG, "heartImageView")
                val userOffersRequest = UserOffersRequest(offer.id, "ABC")
                if (!offer.isFavourite) {
                    offerViewModel.deleteOffer(offer)
                    offerViewModel.deleteUserOffer(token, offer.id)
                } else {
                    offerViewModel.saveOffer(offer)
                    offerViewModel.updateUserOffers(token, userOffersRequest)
                }
            }
        }
        Log.d(TAG, "onItemClick")
    }
}