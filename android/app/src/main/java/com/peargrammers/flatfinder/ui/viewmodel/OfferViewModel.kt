package com.peargrammers.flatfinder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peargrammers.flatfinder.model.Offer
import com.peargrammers.flatfinder.repository.OfferRepository
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class OfferViewModel(private val offerRepository: OfferRepository) : ViewModel() {
    val offers: MutableLiveData<Resource<List<Offer>>> = MutableLiveData()

    private val TAG = OfferViewModel::class.qualifiedName

    init {
        getOffers()
    }

    private fun getOffers() = viewModelScope.launch {
        Log.d(TAG, "getOffers()")
        offers.postValue(Resource.Loading())
        val response = offerRepository.getOffers()
        offers.postValue(handleOffersResponse(response))
    }

    private fun handleOffersResponse(response: Response<List<Offer>>): Resource<List<Offer>> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}
