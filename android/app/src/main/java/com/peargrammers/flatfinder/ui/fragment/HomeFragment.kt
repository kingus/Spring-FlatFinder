package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.adapter.OfferAdapter
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(R.layout.home_fragment) {
    lateinit var viewModel: OfferViewModel
//    lateinit var offersAdapter: OfferAdapter
    private val TAG = HomeFragment::class.qualifiedName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        viewModel = (activity as HomeActivity).offerViewModel
//        setupRecyclerView()

    }
//
//    private fun setupRecyclerView() {
//        offersAdapter = OfferAdapter()
//        rvOffers.apply {
//            adapter = offersAdapter
//            layoutargumentManager = LinearLayoutManager(activity)
//        }
//    }

}
