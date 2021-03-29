package com.peargrammers.flatfinder.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.ui.activities.AuthActivity
import com.peargrammers.flatfinder.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.login_activity.loginButton
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registerFragment = RegisterFragment()

        loginButton.setOnClickListener {
            val intent = Intent (activity, HomeActivity::class.java)
            activity?.startActivity(intent)
        }

        registerTextView.setOnClickListener {
            (activity as AuthActivity).replaceFragment(R.id.fragmentFL, registerFragment)
        }

    }
}