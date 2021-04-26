package com.peargrammers.flatfinder.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.ui.fragment.FavouritesFragment
import com.peargrammers.flatfinder.ui.fragment.LoginFragment
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModel
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModelProviderFactory
import kotlinx.android.synthetic.main.home_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AuthActivity : AppCompatActivity(), KodeinAware {
    private val TAG = AuthActivity::class.qualifiedName
    override val kodein by kodein()
    lateinit var viewModel: LoginViewModel
    private val factory: LoginViewModelProviderFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        setContentView(R.layout.auth_activity)

    }
}