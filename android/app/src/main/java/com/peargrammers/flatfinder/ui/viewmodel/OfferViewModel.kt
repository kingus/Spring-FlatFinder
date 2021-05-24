package com.peargrammers.flatfinder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.peargrammers.flatfinder.dao.UserOffersRequest
import com.peargrammers.flatfinder.model.UserOffer
import com.peargrammers.flatfinder.repository.OfferRepository
import com.peargrammers.flatfinder.repository.UserOffersRepository
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class OfferViewModel(
    private val offerRepository: OfferRepository,
    private val userOffersRepository: UserOffersRepository
) : ViewModel() {
    val offers: MutableLiveData<Resource<List<UserOffer>>> = MutableLiveData()

    private val TAG = OfferViewModel::class.qualifiedName

    init {
    }

    fun getOffers(auth: String) = viewModelScope.launch {
        Log.d(TAG, "getOffers()")
        offers.postValue(Resource.Loading())
        val response = offerRepository.getOffers(auth)
        offers.postValue(handleOffersResponse(response))
    }

    fun getFavouriteOffers(auth: String) = viewModelScope.launch {
        Log.d(TAG, "getFavouriteOffers()")
        val response = userOffersRepository.getUserOffers(auth)
        response.body()?.map {
            it.isFavourite = true
            saveOffer(it)
            Log.d("it isFav", it.isFavourite.toString())
        }
    }

    fun sendEmail(auth: String, id: Int) = viewModelScope.launch {
        Log.d(TAG, "sendEmail()")
        val response = offerRepository.sendEmail(auth, id)
    }

    fun deleteUserOffer(auth: String, id: Int) = viewModelScope.launch {
        Log.d(TAG, "sendEmail()")
        val response = offerRepository.deleteUserOffer(auth, id)
    }

    private fun handleOffersResponse(response: Response<List<UserOffer>>): Resource<List<UserOffer>> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getSavedOffers() = offerRepository.getSavedOffers()

    fun saveOffer(offer: UserOffer) = viewModelScope.launch {
        offerRepository.insert(offer)
    }

    fun deleteOffer(offer: UserOffer) = viewModelScope.launch {
        offerRepository.delete(offer)
    }

    fun updateUserOffers(token: String, userOffersRequest: UserOffersRequest) =
        viewModelScope.launch {
            Log.d(TAG, "updateUserOffers()")
            Log.d(TAG, userOffersRequest.note)
            Log.d(TAG, userOffersRequest.offer_id.toString())
            val response = userOffersRepository.updateUserOffer(token, userOffersRequest)
        }
    fun searchUserOffer(searchQuery: String) : LiveData<List<UserOffer>>{
        return offerRepository.searchUserOffers(searchQuery).asFlow().asLiveData()
    }

}
