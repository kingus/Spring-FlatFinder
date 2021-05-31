package com.peargrammers.flatfinder.repository

import com.peargrammers.flatfinder.api.RetrofitInstance
import com.peargrammers.flatfinder.db.OfferDatabase
import com.peargrammers.flatfinder.model.UserOffer

class OfferRepository(val offerDatabase: OfferDatabase) {

    suspend fun getOffers(auth: String) =
        RetrofitInstance.api.getOffers(auth)

    suspend fun sendEmail(auth: String, id: Int) =
        RetrofitInstance.api.sendEmail(auth, id)

    suspend fun deleteUserOffer(auth: String, id: Int) =
        RetrofitInstance.api.deleteUserOffer(auth, id)

    suspend fun insert(offer: UserOffer) = offerDatabase.getOfferDao().insert(offer)

    suspend fun update(offer: UserOffer) = offerDatabase.getOfferDao().update(offer)

    suspend fun delete(offer: UserOffer) = offerDatabase.getOfferDao().delete(offer)

    fun getSavedOffers() = offerDatabase.getOfferDao().getAll()

    fun searchUserOffers(query: String) = offerDatabase.getOfferDao().searchUserOffer(query)

}