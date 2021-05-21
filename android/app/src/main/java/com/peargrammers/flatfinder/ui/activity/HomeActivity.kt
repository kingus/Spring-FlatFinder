package com.peargrammers.flatfinder.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.databinding.HomeActivityBinding
import com.peargrammers.flatfinder.ui.viewmodel.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {
    private val TAG = HomeActivity::class.qualifiedName
    override val kodein by kodein()
    lateinit var offerViewModel: OfferViewModel
    lateinit var profileViewModel: ProfileViewModel
    lateinit var userOfferViewModel: UserOfferViewModel
    private val factory: OfferViewModelProviderFactory by instance()
    private val profileFactory: ProfileViewModelProviderFactory by instance()
    private val userOfferFactory: UserOfferViewModelProviderFactory by instance()
    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate")
        binding.bottomNavigationView.setupWithNavController(findNavController(R.id.navHostFragment))

        offerViewModel = ViewModelProvider(this, factory).get(OfferViewModel::class.java)
        profileViewModel = ViewModelProvider(this, profileFactory).get(ProfileViewModel::class.java)
        userOfferViewModel =
            ViewModelProvider(this, userOfferFactory).get(UserOfferViewModel::class.java)
    }

}