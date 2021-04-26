package com.peargrammers.flatfinder.repository

import com.peargrammers.flatfinder.api.RetrofitInstance

class ProfileRepository {
    suspend fun getMe(token: String) =
        RetrofitInstance.api.getMe(token)
}