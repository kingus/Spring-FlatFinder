package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.*;
import com.wat.flatfinder.entities.UserOffers;

import java.util.List;

public interface UserOffersService {
    List<UserOffersResponse> getAll(String username);
    void addUserOffers(UserOffersRequest userOffersRequest, String username);
    void deleteUserOffer(int id, String username);
    void updateUserOfferNote(int id, String username, UpdateNoteRequest updateNoteRequest);
}
