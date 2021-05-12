package com.peargrammers.flatfinder.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserOffer(
    var id: Int? = null,
    var district: String?,
    var area: Float?,
    var imgUrl: String?,
    var latitude: Double?,
    var longitude: Double?,
    var offerUrl: String?,
    var price: Float?,
    var rooms: String?,
    var source: String?,
    var sourceId: String?,
    var title: String?,
    @SerializedName("is_favourite")
    var isFavourite: Boolean?
) : Serializable {
    constructor(userOffer: UserOffer, isFavourite: Boolean) : this(
        userOffer.id,
        userOffer.district,
        userOffer.area,
        userOffer.imgUrl,
        userOffer.latitude,
        userOffer.longitude,
        userOffer.offerUrl,
        userOffer.price,
        userOffer.rooms,
        userOffer.source,
        userOffer.sourceId,
        userOffer.title,
        isFavourite
    )
}

