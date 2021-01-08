package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.OfferRequest;
import com.wat.flatfinder.dtos.OfferResponse;
import com.wat.flatfinder.dtos.UserOffersRequest;
import com.wat.flatfinder.dtos.UserOffersResponse;
import com.wat.flatfinder.entities.UserOffers;

import java.util.List;

public interface UserOffersService {
    List<UserOffersResponse> getAll(int userId);
    void addUserOffers(UserOffersRequest userOffersRequest);
}
