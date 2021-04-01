package com.peargrammers.flatfinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.peargrammers.flatfinder.repository.OfferRepository

class OfferViewModelProviderFactory(
    val offerRepository: OfferRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OfferViewModel(offerRepository) as T
    }
}