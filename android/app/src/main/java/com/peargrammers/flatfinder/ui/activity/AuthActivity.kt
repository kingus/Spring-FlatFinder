package com.peargrammers.flatfinder.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.ui.fragment.LoginFragment
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModel
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModelProviderFactory
import kotlinx.android.synthetic.main.home_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AuthActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    lateinit var viewModel: LoginViewModel
    private val factory: LoginViewModelProviderFactory by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        setContentView(R.layout.auth_activity)

        val loginFragment = LoginFragment()

        replaceFragment(R.id.fragmentFL, loginFragment)
    }


    fun replaceFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(id, fragment)
            commit()
        }
    }
}
