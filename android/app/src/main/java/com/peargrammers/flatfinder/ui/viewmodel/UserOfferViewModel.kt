package com.peargrammers.flatfinder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peargrammers.flatfinder.dao.UserOffersRequest
import com.peargrammers.flatfinder.model.Offer
import com.peargrammers.flatfinder.repository.UserOffersRepository
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class UserOfferViewModel(private val userOffersRepository: UserOffersRepository) : ViewModel() {

    val userOffers: MutableLiveData<Resource<List<Offer>>> = MutableLiveData()
    private val TAG = UserOfferViewModel::class.qualifiedName

    fun getUserOffers(token: String) = viewModelScope.launch {
        Log.d(TAG, "getOffers()")
        userOffers.postValue(Resource.Loading())
        val response = userOffersRepository.getUserOffers(token)
        userOffers.postValue(handleOffersResponse(response))
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