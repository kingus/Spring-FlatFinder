package com.peargrammers.flatfinder.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModelProviderFactory
import com.peargrammers.flatfinder.ui.viewmodel.ProfileViewModel
import com.peargrammers.flatfinder.ui.viewmodel.ProfileViewModelProviderFactory
import kotlinx.android.synthetic.main.home_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {
    private val TAG = HomeActivity::class.qualifiedName
    override val kodein by kodein()
    lateinit var offerViewModel: OfferViewModel
    lateinit var profileViewModel: ProfileViewModel
    private val factory: OfferViewModelProviderFactory by instance()
    private val profileFactory: ProfileViewModelProviderFactory by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.home_activity)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        offerViewModel = ViewModelProvider(this, factory).get(OfferViewModel::class.java)
        profileViewModel = ViewModelProvider(this, profileFactory).get(ProfileViewModel::class.java)
    }

}