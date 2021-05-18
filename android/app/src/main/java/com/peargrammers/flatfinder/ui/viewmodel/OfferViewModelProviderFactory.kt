package com.peargrammers.flatfinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.peargrammers.flatfinder.repository.OfferRepository
import com.peargrammers.flatfinder.repository.UserOffersRepository

class OfferViewModelProviderFactory(
    val offerRepository: OfferRepository,
    val userOfferRepository: UserOffersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OfferViewModel(offerRepository, userOfferRepository) as T
    }
}