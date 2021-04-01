package com.peargrammers.flatfinder.dao

import com.google.gson.annotations.SerializedName
import com.peargrammers.flatfinder.model.Offer

data class OfferResponse(
    @SerializedName("offers")
    var offers: List<Offer>
)