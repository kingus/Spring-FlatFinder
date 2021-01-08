package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.OfferResponse;
import com.wat.flatfinder.dtos.UserOffersRequest;
import com.wat.flatfinder.dtos.UserOffersResponse;
import com.wat.flatfinder.entities.UserOffers;
import com.wat.flatfinder.repositories.OfferRepository;
import com.wat.flatfinder.repositories.UserOffersRepository;
import com.wat.flatfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserOffersServiceImpl implements UserOffersService{
    private final UserOffersRepository userOffersRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public UserOffersServiceImpl(UserOffersRepository userOffersRepository, UserRepository userRepository, OfferRepository offerRepository) {
        this.userOffersRepository = userOffersRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public List<UserOffersResponse> getAll(int userId) {
                return StreamSupport.stream(userOffersRepository.findAll().spliterator(), false)
                .filter(userOffers -> userOffers.getUser().getId()==userId)
                .map(userOffers -> new UserOffersResponse
                        (userOffers.getOffer().getDistrict(), userOffers.getOffer().getArea(),
                                userOffers.getOffer().getImgUrl(), userOffers.getOffer().getLatitude(), userOffers.getOffer().getLongitude(),userOffers.getOffer().getOfferUrl(),
                                userOffers.getOffer().getPrice(), userOffers.getOffer().getRooms(), userOffers.getOffer().getSource(), userOffers.getOffer().getSourceId(), userOffers.getOffer().getTitle(), userOffers.getNote())).collect(Collectors.toList());
    }

    @Override
    public void addUserOffers(UserOffersRequest userOffersRequest) {
        UserOffers userOffers = new UserOffers();

        if (userRepository.findById(userOffersRequest.getUser_id()).isPresent()){
            userOffers.setUser(userRepository.findById(userOffersRequest.getUser_id()).get());
        }
        if(offerRepository.findById(userOffersRequest.getOffer_id()).isPresent()){
            userOffers.setOffer(offerRepository.findById(userOffersRequest.getOffer_id()).get());
        }
        userOffers.setNote(userOffersRequest.getNote());
        userOffersRepository.save(userOffers);
    }
}
