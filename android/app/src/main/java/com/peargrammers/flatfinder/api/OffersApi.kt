package com.peargrammers.flatfinder.api

import com.peargrammers.flatfinder.dao.MeResponse
import com.peargrammers.flatfinder.dao.UserOffersRequest
import com.peargrammers.flatfinder.model.Offer
import retrofit2.Response
import retrofit2.http.*

interface OffersApi {
    @GET("api/offers")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json"
    )
    suspend fun getOffers(
    ): Response<List<Offer>>

    @GET("api/me")
    suspend fun getMe(
        @Header("Authorization") auth: String
    ): Response<MeResponse>

    @GET("/api/user-offers")
    suspend fun getUserOffers(
        @Header("Authorization") auth: String
    ): Response<List<Offer>>

    @GET("/api/send-email/{id}")
    suspend fun sendEmail(
        @Header("Authorization") auth: String, @Path("id") id: Int
    )

    @POST("/api/user-offers")
    suspend fun updateUserOffer(
        @Header("Authorization") auth: String, @Body userOffersRequest: UserOffersRequest
    )

}