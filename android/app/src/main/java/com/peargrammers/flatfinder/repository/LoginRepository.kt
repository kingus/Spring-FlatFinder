package com.peargrammers.flatfinder.repository

import com.peargrammers.flatfinder.api.RetrofitInstance
import com.peargrammers.flatfinder.dao.LoginRequest

class LoginRepository {
    suspend fun postLogin(loginRequest: LoginRequest) =
        RetrofitInstance.authApi.postLogin(loginRequest)
}