package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.dao.LoginRequest
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.ui.activity.AuthActivity
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModel
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.password_text_input.*
import kotlinx.android.synthetic.main.text_input.*
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.login_fragment) {
    private val TAG = LoginFragment::class.qualifiedName
    lateinit var viewModel: LoginViewModel
    lateinit var username: String
    lateinit var password: String
    lateinit var userPreferencesImpl: UserPreferencesImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as AuthActivity).viewModel
        userPreferencesImpl = UserPreferencesImpl(requireContext())

        loginButton.setOnClickListener {
            Log.d(TAG, "loginButton.setOnClickListener")

            username = loginTextInputET.text.toString()
            password = passwordTextInputET.text.toString()

            viewModel.postLogin(LoginRequest(username, password))
        }

        viewModel.token.observe(viewLifecycleOwner, Observer { response ->
            when (response) {

                is Resource.Success -> {
                    response.data?.let { token ->
                        Log.d(TAG, "login")
                        lifecycleScope.launch {
                            userPreferencesImpl.saveAuthToken(token)
                        }
                    }
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeActivity2)
                }

                is Resource.Error -> {
                    Log.d(TAG, "error")
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured $message")
                    }
                }

                is Resource.Loading -> {
                    Log.d(TAG, "progress")
                }

            }
        })

        registerTextView.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }
}