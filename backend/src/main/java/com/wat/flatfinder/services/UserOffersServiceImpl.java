package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.*;
import com.wat.flatfinder.entities.User;
import com.wat.flatfinder.entities.UserOffers;
import com.wat.flatfinder.repositories.OfferRepository;
import com.wat.flatfinder.repositories.UserOffersRepository;
import com.wat.flatfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<UserOffersResponse> getAll(String username) {
        System.out.println(username);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return StreamSupport.stream(userOffersRepository.findAll().spliterator(), false)
                    .filter(userOffers -> userOffers.getUser().getId() == user.get().getId())
                    .map(userOffers -> new UserOffersResponse
                            (userOffers.getOffer().getDistrict(), userOffers.getOffer().getArea(),
                                    userOffers.getOffer().getImgUrl(), userOffers.getOffer().getLatitude(), userOffers.getOffer().getLongitude(), userOffers.getOffer().getOfferUrl(),
                                    userOffers.getOffer().getPrice(), userOffers.getOffer().getRooms(), userOffers.getOffer().getSource(), userOffers.getOffer().getSourceId(), userOffers.getOffer().getTitle(), userOffers.getNote(), userOffers.getId())).collect(Collectors.toList());
        }
        return new ArrayList<UserOffersResponse>();
    }

    @Override
    public void addUserOffers(UserOffersRequest userOffersRequest, String username) {
        UserOffers userOffers = new UserOffers();
        boolean isCorrect = true;
        Optional<User> user = userRepository.findByUsername(username);

        Optional<UserOffers> userOffer = userOffersRepository.findByUserIdAndOfferId(user.get().getId(), userOffersRequest.getOffer_id());

        if(userOffer.isEmpty()){
            userOffers.setUser(user.get());
            userOffers.setOffer(offerRepository.findById(userOffersRequest.getOffer_id()).get());
            userOffers.setNote(userOffersRequest.getNote());
            userOffersRepository.save(userOffers);

        }else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteUserOffer(int id, String username) {
        int user_id = userRepository.findByUsername(username).get().getId();
        System.out.println(user_id);
        System.out.println("ID" + id);
        try{
            Optional<UserOffers> userOffer = userOffersRepository.findByUserIdAndOfferId(user_id, id);
            if(userOffer.isPresent()){
                userOffersRepository.delete(userOffer.get());
            }
        }catch (Exception ex){
            throw new RuntimeException("User offer not found");
        }
    }
    @Override
    public void updateUserOfferNote(int id, String username, UpdateNoteRequest updateNoteRequest) {
        int user_id = userRepository.findByUsername(username).get().getId();
        System.out.println(user_id);
        System.out.println("ID" + id);
        try{
            Optional<UserOffers> userOffer = userOffersRepository.findByUserIdAndOfferId(user_id, id);
            if (userOffer.isPresent()){
                userOffer.get().setNote(updateNoteRequest.getNote());
                userOffersRepository.save(userOffer.get());
            }
        }catch (Exception ex){
            throw new RuntimeException("User offer not found");
        }
    }
}
