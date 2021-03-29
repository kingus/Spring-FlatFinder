package com.peargrammers.flatfinder.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.peargrammers.flatfinder.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }
}