package com.peargrammers.flatfinder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peargrammers.flatfinder.dao.LoginRequest
import com.peargrammers.flatfinder.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val TAG = LoginViewModel::class.qualifiedName

    fun postLogin(loginRequest: LoginRequest) = viewModelScope.launch {
        Log.d(TAG, "postLogin()")
        val response = loginRepository.postLogin(loginRequest)
        Log.d("postLogin()", response.headers().get("Authorization"))


    }

}