package com.peargrammers.flatfinder.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.ui.viewmodel.*
import kotlinx.android.synthetic.main.home_activity.*
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.home_activity)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        offerViewModel = ViewModelProvider(this, factory).get(OfferViewModel::class.java)
        profileViewModel = ViewModelProvider(this, profileFactory).get(ProfileViewModel::class.java)
        userOfferViewModel =
            ViewModelProvider(this, userOfferFactory).get(UserOfferViewModel::class.java)
    }

}