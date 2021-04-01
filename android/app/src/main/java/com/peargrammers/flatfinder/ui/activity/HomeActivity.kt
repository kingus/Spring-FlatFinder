package com.peargrammers.flatfinder.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.model.Offer
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModelProviderFactory
import kotlinx.android.synthetic.main.home_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    lateinit var viewModel: OfferViewModel
    private val factory: OfferViewModelProviderFactory by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        viewModel = ViewModelProvider(this, factory).get(OfferViewModel::class.java)

    }
}