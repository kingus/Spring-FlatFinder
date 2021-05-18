package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.peargrammers.flatfinder.R
import kotlinx.android.synthetic.main.offer_fragment.*

class OfferFragment : Fragment(R.layout.offer_fragment) {
    val args : OfferFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val offer = args.offer

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(offer.offerUrl)
        }
    }
}