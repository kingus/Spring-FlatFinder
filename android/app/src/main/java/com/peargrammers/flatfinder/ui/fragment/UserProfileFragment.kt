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
import androidx.navigation.findNavController
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.ui.viewmodel.ProfileViewModel
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.coroutines.runBlocking

class UserProfileFragment : Fragment(R.layout.user_profile_fragment) {
    private val TAG = UserProfileFragment::class.qualifiedName
    lateinit var viewModel: ProfileViewModel
    lateinit var userPreferencesImpl: UserPreferencesImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).profileViewModel

        Log.d(TAG, activity.toString())

        userPreferencesImpl = UserPreferencesImpl(requireContext())

        userPreferencesImpl.authToken.asLiveData().observe(viewLifecycleOwner, Observer { token ->

            if (token != null) {
                viewModel.getMe(token)
            }

        })

        logoutButton.setOnClickListener {
            logout()
        }

        viewModel.userData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d(TAG, "Response")
                    usernameTextView.text = response.data?.username
                    emailTextView.text = response.data?.email
                    districtTextView.text = response.data?.preffered_district
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured $message")
                    }
                }
            }
        })

    }

    fun logout() {
        view?.findNavController()?.navigate(R.id.action_userProfileFragment_to_authActivity)
        runBlocking {
            userPreferencesImpl.saveAuthToken("")
        }
    }
}