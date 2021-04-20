package com.peargrammers.flatfinder.dao

import com.google.gson.annotations.SerializedName
data class OfferResponse(
    @SerializedName("offers")
    var offers: List<OfferResponse>
)