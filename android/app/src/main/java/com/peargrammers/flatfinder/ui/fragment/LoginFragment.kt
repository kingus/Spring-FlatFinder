package com.peargrammers.flatfinder.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.dao.LoginRequest
import com.peargrammers.flatfinder.ui.activity.AuthActivity
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModel
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {
    private val TAG = LoginFragment::class.qualifiedName
    lateinit var viewModel: LoginViewModel
    lateinit var username: String
    lateinit var password: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registerFragment = RegisterFragment()
        viewModel = (activity as AuthActivity).viewModel

        loginButton.setOnClickListener {
            Log.d(TAG, "loginButton.setOnClickListener")

            username = loginEditText.text.toString()
            password = passwordEditText.text.toString()
            Log.d(TAG, username)
            Log.d(TAG, password)
            viewModel.postLogin(LoginRequest(username, password))
        }

        val sharedPref = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()

        viewModel.token.observe(viewLifecycleOwner, Observer { response ->
            when (response) {

                is Resource.Success -> {
                    response.data?.let { token ->
                        Log.d(TAG, "login")
                        editor?.apply {
                            putString("token", token)
                            apply()
                        }
                    }
//                    val intent = Intent(activity, HomeActivity::class.java)
//                    activity?.startActivity(intent)
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