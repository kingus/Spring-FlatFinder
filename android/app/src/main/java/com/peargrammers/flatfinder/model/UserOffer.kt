package com.peargrammers.flatfinder.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "user_offer")
data class UserOffer(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("district")
    var district: String,
    @SerializedName("area")
    var area: Float,
    @SerializedName("img_url")
    var imgUrl: String,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("offer_url")
    var offerUrl: String,
    @SerializedName("price")
    var price: Float,
    @SerializedName("rooms")
    var rooms: String,
    @SerializedName("source")
    var source: String,
    @SerializedName("source_id")
    var sourceId: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("favourite")
    var isFavourite: Boolean = true,
    @SerializedName("email")
    var isEmailSent: Boolean = true
) : Serializable
