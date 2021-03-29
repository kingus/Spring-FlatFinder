package com.peargrammers.flatfinder.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.peargrammers.flatfinder.R
import kotlinx.android.synthetic.main.login_activity.*

class LoginFragment : Fragment(R.layout.login_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener {

        }
    }

}