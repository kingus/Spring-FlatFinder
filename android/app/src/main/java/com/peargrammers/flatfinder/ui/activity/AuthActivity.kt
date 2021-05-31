package com.peargrammers.flatfinder.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.peargrammers.flatfinder.databinding.AuthActivityBinding
import com.peargrammers.flatfinder.ui.viewmodel.AuthViewModel
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModelProviderFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AuthActivity : AppCompatActivity(), KodeinAware {
    private val TAG = AuthActivity::class.qualifiedName
    override val kodein by kodein()
    lateinit var viewModel: AuthViewModel
    private val factory: LoginViewModelProviderFactory by instance()
    private lateinit var binding: AuthActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
    }
}