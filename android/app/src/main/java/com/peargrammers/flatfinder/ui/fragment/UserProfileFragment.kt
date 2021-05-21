package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.peargrammers.flatfinder.databinding.UserProfileFragmentBinding
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.ui.viewmodel.ProfileViewModel
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.coroutines.runBlocking

class UserProfileFragment : Fragment(R.layout.user_profile_fragment) {
    private val TAG = UserProfileFragment::class.qualifiedName
    lateinit var viewModel: ProfileViewModel
    lateinit var userPreferencesImpl: UserPreferencesImpl

    private var _binding: UserProfileFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UserProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


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

        binding.logoutButton.setOnClickListener {
            logout(view)
        }

        viewModel.userData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d(TAG, "Response")
                    binding.usernameTextView.text = response.data?.username
                    binding.emailTextView.text = response.data?.email
                    binding.districtTextView.text = response.data?.preffered_district
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured $message")
                    }
                }
            }
        })

    }

    private fun logout(view: View) {
        view.findNavController().navigate(R.id.action_userProfileFragment_to_authActivity)
        runBlocking {
            userPreferencesImpl.saveAuthToken("")
        }
    }
}