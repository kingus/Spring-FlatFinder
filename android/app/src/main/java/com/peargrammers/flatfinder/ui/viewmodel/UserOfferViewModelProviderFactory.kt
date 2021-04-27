package com.peargrammers.flatfinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.peargrammers.flatfinder.repository.OfferRepository
import com.peargrammers.flatfinder.repository.UserOffersRepository

class UserOfferViewModelProviderFactory(
    val userOffersRepository: UserOffersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserOfferViewModel(userOffersRepository) as T
    }
}