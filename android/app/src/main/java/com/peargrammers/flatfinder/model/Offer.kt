package com.peargrammers.flatfinder.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity(tableName = "offer")
data class Offer(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("district")
    var district: String?,
    @SerializedName("area")
    var area: Float?,
    @SerializedName("img_url")
    var imgUrl: String?,
    @SerializedName("latitude")
    var latitude: Double?,
    @SerializedName("longitude")
    var longitude: Double?,
    @SerializedName("offer_url")
    var offerUrl: String?,
    @SerializedName("price")
    var price: Float?,
    @SerializedName("rooms")
    var rooms: String?,
    @SerializedName("source")
    var source: String?,
    @SerializedName("source_id")
    var sourceId: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("start_dttm")
    var startDttm: LocalDate?,
    @SerializedName("end_dttm")
    var endDttm: LocalDate?
) : Serializable
