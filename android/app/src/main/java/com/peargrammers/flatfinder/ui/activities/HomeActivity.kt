package com.peargrammers.flatfinder.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peargrammers.flatfinder.R
import kotlinx.android.synthetic.main.main_activity.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }
}