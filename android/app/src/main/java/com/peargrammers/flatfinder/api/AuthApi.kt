package com.peargrammers.flatfinder.api

import com.peargrammers.flatfinder.dao.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun postLogin(@Body loginRequest: LoginRequest) : Response<Void>
}