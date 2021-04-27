package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import kotlinx.android.synthetic.main.user_profile_fragment.*
import android.util.Log
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.ui.viewmodel.ProfileViewModel
import com.peargrammers.flatfinder.utils.Resource

class UserProfileFragment : Fragment(R.layout.user_profile_fragment) {
    private val TAG = UserProfileFragment::class.qualifiedName
    lateinit var viewModel: ProfileViewModel
    lateinit var token: String
    lateinit var userPreferencesImpl: UserPreferencesImpl
    private val KEY_AUTH = preferencesKey<String>("key_auth")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).profileViewModel
        Log.d(TAG, activity.toString())
        userPreferencesImpl = UserPreferencesImpl(requireContext())

        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { token ->

            if (token!=null) {
                viewModel.getMe(token)
            }

        })

        viewModel.userData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d(TAG, "Response")
                    usernameTextView.text = response.data?.username
                    emailTextView.text = response.data?.email

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