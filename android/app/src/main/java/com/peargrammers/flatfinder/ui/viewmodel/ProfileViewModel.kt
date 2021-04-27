package com.peargrammers.flatfinder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peargrammers.flatfinder.dao.MeResponse
import com.peargrammers.flatfinder.repository.ProfileRepository
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    val userData: MutableLiveData<Resource<MeResponse>> = MutableLiveData()

    private val TAG = ProfileViewModel::class.qualifiedName

    fun getMe(token: String) = viewModelScope.launch {
        userData.postValue(Resource.Loading())
        Log.d(TAG, token)
        val response = profileRepository.getMe(token)
        userData.postValue(handleUserDataResponse(response))
        Log.d(TAG, userData.toString())

    }

    private fun handleUserDataResponse(response: Response<MeResponse>): Resource<MeResponse> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Log.d(TAG, resultResponse.email)
                Log.d(TAG, resultResponse.preffered_district)
                Log.d(TAG, resultResponse.username)
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}