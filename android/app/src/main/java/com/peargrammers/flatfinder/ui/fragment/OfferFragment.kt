package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.databinding.OfferFragmentBinding

class OfferFragment : Fragment(R.layout.offer_fragment) {
    val args: OfferFragmentArgs by navArgs()

    private var _binding: OfferFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OfferFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val offer = args.offer

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(offer.offerUrl)
        }
    }
}