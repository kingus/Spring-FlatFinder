package com.peargrammers.flatfinder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peargrammers.flatfinder.dao.LoginRequest
import com.peargrammers.flatfinder.repository.LoginRepository
import com.peargrammers.flatfinder.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    val token: MutableLiveData<Resource<String>> = MutableLiveData()

    private val TAG = LoginViewModel::class.qualifiedName

    fun postLogin(loginRequest: LoginRequest) = viewModelScope.launch {
        Log.d(TAG, "postLogin()")
        token.postValue(Resource.Loading())
        val response = loginRepository.postLogin(loginRequest)
        token.postValue(handleTokenResponse(response))
    }

    private fun handleTokenResponse(response: Response<Void>): Resource<String> {

        if (response.isSuccessful) {
            val token = response.headers().get("Authorization")
            Log.d("handleTokenResponse()", token)
            token?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}