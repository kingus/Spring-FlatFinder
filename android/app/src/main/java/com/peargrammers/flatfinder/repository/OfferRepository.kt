package com.peargrammers.flatfinder.repository

import com.peargrammers.flatfinder.api.RetrofitInstance

class OfferRepository() {

    suspend fun getOffers() =
        RetrofitInstance.api.getOffers()

    suspend fun getMe(auth: String) =
        RetrofitInstance.api.getMe(auth)

}