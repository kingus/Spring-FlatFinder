package com.peargrammers.flatfinder.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.dao.LoginRequest
import com.peargrammers.flatfinder.ui.activity.AuthActivity
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity.loginButton
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {
    private val TAG = LoginFragment::class.qualifiedName
    lateinit var viewModel: LoginViewModel
    lateinit var username : String
    lateinit var password : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registerFragment = RegisterFragment()
        viewModel = (activity as AuthActivity).viewModel

        loginButton.setOnClickListener {
           username = loginEditText.text.toString()
           password = passwordEditText.text.toString()
            Log.d(TAG, username)
            Log.d(TAG, password)
            viewModel.postLogin(LoginRequest(username, password))
//            val intent = Intent (activity, HomeActivity::class.java)
//            activity?.startActivity(intent)
        }

        registerTextView.setOnClickListener {
            (activity as AuthActivity).replaceFragment(R.id.fragmentFL, registerFragment)
        }
    }
}