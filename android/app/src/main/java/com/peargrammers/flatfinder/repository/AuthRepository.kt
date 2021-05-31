package com.peargrammers.flatfinder.repository

import com.peargrammers.flatfinder.api.RetrofitInstance
import com.peargrammers.flatfinder.dao.LoginRequest
import com.peargrammers.flatfinder.dao.RegisterRequest

class AuthRepository {
    suspend fun postLogin(loginRequest: LoginRequest) =
        RetrofitInstance.authApi.postLogin(loginRequest)

    suspend fun postRegister(registerRequest: RegisterRequest) =
        RetrofitInstance.authApi.postRegister(registerRequest)
}