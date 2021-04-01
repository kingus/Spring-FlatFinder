package com.peargrammers.flatfinder.repository

import com.peargrammers.flatfinder.model.Offer
import java.time.LocalDate

class OfferRepository() {

    private val offersList = mutableListOf<Offer>()

    init {
        var date = LocalDate.of(2018, 12, 31)
        val offer1 = Offer(
            1,
            "wola",
            42.0f,
            "www.otodom.com",
            12.0,
            12.0,
            "www.otodom.com",
            12.2f,
            "2",
            "otodom",
            "123",
            "Mieszkanie na sprzedaż",
            date,
            date
        )
        offersList.add(offer1)
        offersList.add(offer1)
        offersList.add(offer1)
        offersList.add(offer1)
        offersList.add(offer1)
    }

    fun getOffers() = offersList;


}