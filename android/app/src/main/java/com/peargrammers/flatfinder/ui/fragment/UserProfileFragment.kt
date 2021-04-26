package com.peargrammers.flatfinder.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import kotlinx.android.synthetic.main.user_profile_fragment.*
import android.util.Log
import androidx.lifecycle.Observer
import com.peargrammers.flatfinder.ui.viewmodel.ProfileViewModel
import com.peargrammers.flatfinder.utils.Resource

class UserProfileFragment : Fragment(R.layout.user_profile_fragment) {
    private val TAG = UserProfileFragment::class.qualifiedName
    lateinit var viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).profileViewModel
        Log.d(TAG, activity.toString())

        val sharedPref = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("token", "token")
        usernameTextView.text = token
        val userToken = if (token!=null) token else "token"

        viewModel.userData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d(TAG, "Response")
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured $message")
                    }
                }
            }
        })

    }
}