package com.peargrammers.flatfinder.repository

import com.peargrammers.flatfinder.api.RetrofitInstance
import com.peargrammers.flatfinder.dao.AddUserOfferRequest
import com.peargrammers.flatfinder.dao.UserOffersRequest

class UserOffersRepository {

    suspend fun getUserOffers(auth: String) =
        RetrofitInstance.api.getUserOffers(auth)

    suspend fun updateUserOffer(auth: String, offerId: Int, userOffersRequest: UserOffersRequest) =
        RetrofitInstance.api.updateUserOffer(auth, offerId, userOffersRequest)

    suspend fun addUserOffer(auth: String, addUserOfferRequest: AddUserOfferRequest) =
        RetrofitInstance.api.addUserOffer(auth, addUserOfferRequest)

}