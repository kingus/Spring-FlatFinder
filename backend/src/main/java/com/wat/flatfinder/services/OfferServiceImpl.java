package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.OfferRequest;
import com.wat.flatfinder.dtos.OfferResponse;
import com.wat.flatfinder.entities.Offer;
import com.wat.flatfinder.repositories.OfferRepository;
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

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<OfferResponse> getAll() {

            return StreamSupport.stream(offerRepository.findAll().spliterator(), false)
                    .map(offerEntity -> new OfferResponse
                            (offerEntity.getId(), offerEntity.getDistrict(), offerEntity.getArea(),
                                    offerEntity.getImgUrl(), offerEntity.getLatitude(), offerEntity.getLongitude(),offerEntity.getOfferUrl(),
                                    offerEntity.getPrice(), offerEntity.getRooms(), offerEntity.getSource(), offerEntity.getSourceId(), offerEntity.getTitle())).collect(Collectors.toList());
    }

    @Override
    public void addOffer(OfferRequest offerRequest) {
        String input = "31/12/9999 23:59:59" ;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "dd/MM/uuuu HH:mm:ss" ) ;
        LocalDateTime ldt = LocalDateTime.parse( input , dtf) ;
        Offer offer =  new Offer();
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
    }
}
