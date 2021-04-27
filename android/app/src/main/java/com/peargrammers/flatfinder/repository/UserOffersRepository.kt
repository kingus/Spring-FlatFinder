package com.peargrammers.flatfinder.repository

import com.peargrammers.flatfinder.api.RetrofitInstance

class UserOffersRepository {

    suspend fun getUserOffers(auth: String) =
        RetrofitInstance.api.getUserOffers(auth)

}