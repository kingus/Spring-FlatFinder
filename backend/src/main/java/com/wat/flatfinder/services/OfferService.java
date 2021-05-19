package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.OfferAllResponse;
import com.wat.flatfinder.dtos.OfferRequest;
import com.wat.flatfinder.dtos.OfferResponse;

import java.util.List;

public interface OfferService {
    List<OfferResponse> getAll();
    List<OfferAllResponse> getAllOffers(String username);
    void addOffer(OfferRequest offerRequest);
}
