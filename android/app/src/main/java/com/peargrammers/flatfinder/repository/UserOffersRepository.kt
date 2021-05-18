package com.peargrammers.flatfinder.repository

import com.peargrammers.flatfinder.api.RetrofitInstance
import com.peargrammers.flatfinder.dao.UserOffersRequest

class UserOffersRepository {

    suspend fun getUserOffers(auth: String) =
        RetrofitInstance.api.getUserOffers(auth)

    suspend fun updateUserOffer(auth: String, userOffersRequest: UserOffersRequest) =
        RetrofitInstance.api.updateUserOffer(auth,userOffersRequest)

}