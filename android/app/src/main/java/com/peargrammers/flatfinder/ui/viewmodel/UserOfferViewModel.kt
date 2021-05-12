package com.peargrammers.flatfinder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peargrammers.flatfinder.dao.UserOffersRequest
import com.peargrammers.flatfinder.model.Offer
import com.peargrammers.flatfinder.model.UserOffer
import com.peargrammers.flatfinder.repository.UserOffersRepository
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class UserOfferViewModel(private val userOffersRepository: UserOffersRepository) : ViewModel() {

    val offers: MutableLiveData<Resource<List<Offer>>> = MutableLiveData()
    private val TAG = UserOfferViewModel::class.qualifiedName

    fun getUserOffers(token: String) = viewModelScope.launch {
        Log.d(TAG, "getUserOffers()")
        offers.postValue(Resource.Loading())
        val response = userOffersRepository.getUserOffers(token)
        offers.postValue(handleOffersResponse(response))
    }

    fun updateUserOffers(token: String, userOffersRequest: UserOffersRequest) =
        viewModelScope.launch {
            Log.d(TAG, "updateUserOffers()")
            Log.d(TAG, userOffersRequest.note)
            Log.d(TAG, userOffersRequest.offer_id.toString())
            val response = userOffersRepository.updateUserOffer(token, userOffersRequest)
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