package com.peargrammers.flatfinder.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peargrammers.flatfinder.dao.OfferResponse
import com.peargrammers.flatfinder.repository.OfferRepository
import com.peargrammers.flatfinder.utils.Resource


class OfferViewModel(private val offerRepository: OfferRepository) : ViewModel() {
    val offers: MutableLiveData<Resource<OfferResponse>> = MutableLiveData()

    fun getOffers() = offerRepository.getOffers()


}
