package com.peargrammers.flatfinder.api

import com.peargrammers.flatfinder.model.Offer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface OffersApi {
    @GET("api/offers")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json"
    )
    suspend fun getOffers(
    ): Response<List<Offer>>
}