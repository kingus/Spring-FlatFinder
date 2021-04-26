package com.peargrammers.flatfinder.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.peargrammers.flatfinder.R
import kotlinx.android.synthetic.main.favourites_fragment.*

class FavouritesFragment : Fragment(R.layout.favourites_fragment) {
    private val TAG = FavouritesFragment::class.qualifiedName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, activity.toString())
        val sharedPref = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("token", "token")
        tokenTextView.text = token
    }
}