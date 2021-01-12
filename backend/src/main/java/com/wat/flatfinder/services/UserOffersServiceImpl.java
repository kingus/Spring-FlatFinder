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
        User user = userRepository.findByUsername(username);
        int userId = user.getId();

        System.out.println(user.getId());
                return StreamSupport.stream(userOffersRepository.findAll().spliterator(), false)
                .filter(userOffers -> userOffers.getUser().getId()==userId)
                .map(userOffers -> new UserOffersResponse
                        (userOffers.getOffer().getDistrict(), userOffers.getOffer().getArea(),
                                userOffers.getOffer().getImgUrl(), userOffers.getOffer().getLatitude(), userOffers.getOffer().getLongitude(),userOffers.getOffer().getOfferUrl(),
                                userOffers.getOffer().getPrice(), userOffers.getOffer().getRooms(), userOffers.getOffer().getSource(), userOffers.getOffer().getSourceId(), userOffers.getOffer().getTitle(), userOffers.getNote(), userOffers.getOffer().getId())).collect(Collectors.toList());
    }

    @Override
    public void addUserOffers(UserOffersRequest userOffersRequest, String username) {
        UserOffers userOffers = new UserOffers();
        boolean isCorrect = true;
        System.out.println(userOffersRequest.getOffer_id());
        System.out.println(userOffersRequest.getNote());

//        User user = userRepository.findByUsername(username);
        userOffers.setUser(userRepository.findByUsername(username));
        System.out.println("userPresent");

        if(offerRepository.findById(userOffersRequest.getOffer_id()).isPresent()){
            userOffers.setOffer(offerRepository.findById(userOffersRequest.getOffer_id()).get());
            System.out.println("offerPresent");

        }else{
            isCorrect = false;
            System.out.println("offernotPresent");

        }

        userOffers.setNote(userOffersRequest.getNote());
        if (isCorrect){
            userOffersRepository.save(userOffers);

        }
    }

    @Override
    public void deleteUserOffer(int id, String username) {
        int user_id = userRepository.findByUsername(username).getId();
        System.out.println(user_id);
        System.out.println("ID" + id);
        try{
            UserOffers userOffer = userOffersRepository.findByUserIdAndOfferId(user_id, id);
            userOffersRepository.delete(userOffer);
        }catch (Exception ex){
            throw new RuntimeException("User offer not found");
        }
    }
    @Override
    public void updateUserOfferNote(int id, String username, UpdateNoteRequest updateNoteRequest) {
        int user_id = userRepository.findByUsername(username).getId();
        System.out.println(user_id);
        System.out.println("ID" + id);
        try{
            UserOffers userOffer = userOffersRepository.findByUserIdAndOfferId(user_id, id);
            userOffer.setNote(updateNoteRequest.getNote());
            userOffersRepository.save(userOffer);
            System.out.println(userOffer.getNote());
        }catch (Exception ex){
            throw new RuntimeException("User offer not found");
        }
    }
}
