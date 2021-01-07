package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.OfferRequest;
import com.wat.flatfinder.dtos.OfferResponse;

import java.util.List;

public interface OfferService {
    List<OfferResponse> getAll();
    void addOffer(OfferRequest offerRequest);
}
