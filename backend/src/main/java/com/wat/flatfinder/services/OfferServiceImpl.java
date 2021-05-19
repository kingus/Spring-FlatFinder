package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.OfferAllResponse;
import com.wat.flatfinder.dtos.OfferRequest;
import com.wat.flatfinder.dtos.OfferResponse;
import com.wat.flatfinder.entities.Offer;
import com.wat.flatfinder.repositories.OfferRepository;
import com.wat.flatfinder.repositories.UserOffersRepository;
import com.wat.flatfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final UserOffersRepository userOffersRepository;
    private final UserRepository userRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, UserOffersRepository userOffersRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userOffersRepository = userOffersRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OfferResponse> getAll() {

        return StreamSupport.stream(offerRepository.findAll().spliterator(), false)
                .map(offerEntity -> new OfferResponse
                        (offerEntity.getId(), offerEntity.getDistrict(), offerEntity.getArea(),
                                offerEntity.getImgUrl(), offerEntity.getLatitude(), offerEntity.getLongitude(), offerEntity.getOfferUrl(),
                                offerEntity.getPrice(), offerEntity.getRooms(), offerEntity.getSource(), offerEntity.getSourceId(), offerEntity.getTitle())).collect(Collectors.toList());
    }

    @Override
    public List<OfferAllResponse> getAllOffers(String username) {
        int userId = userRepository.findByUsername(username).get().getId();
        return StreamSupport.stream(offerRepository.findAll().spliterator(), false)
                .map(offerEntity -> new OfferAllResponse
                        (offerEntity.getId(), offerEntity.getDistrict(), offerEntity.getArea(),
                                offerEntity.getImgUrl(), offerEntity.getLatitude(), offerEntity.getLongitude(), offerEntity.getOfferUrl(),
                                offerEntity.getPrice(), offerEntity.getRooms(), offerEntity.getSource(), offerEntity.getSourceId(), offerEntity.getTitle(), (userOffersRepository.findByUserIdAndOfferId(userId, offerEntity.getId()).isPresent()))
                ).collect(Collectors.toList());

    }

    @Override
    public void addOffer(OfferRequest offerRequest) {
        if (offerRepository.findBySourceId(offerRequest.getSource_id()).isEmpty()) {
            String input = "31/12/9999 23:59:59";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.parse(input, dtf);
            Offer offer = new Offer();
            offer.setArea(offerRequest.getArea());
            offer.setDistrict(offerRequest.getDistrict());
            offer.setImgUrl(offerRequest.getImg_url());
            offer.setLatitude(offerRequest.getLatitude());
            offer.setLongitude(offerRequest.getLongitude());
            offer.setOfferUrl(offerRequest.getOffer_url());
            offer.setPrice(offerRequest.getPrice());
            offer.setRooms(offerRequest.getRooms());
            offer.setSource(offerRequest.getSource());
            offer.setSourceId(offerRequest.getSource_id());
            offer.setTitle(offerRequest.getTitle());
            offer.setStartDttm(ldt);
            offer.setEndDttm(LocalDateTime.now());
            offerRepository.save(offer);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
