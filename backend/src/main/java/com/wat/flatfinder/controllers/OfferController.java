package com.wat.flatfinder.controllers;

import com.wat.flatfinder.dtos.OfferRequest;
import com.wat.flatfinder.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService apartamentService) {
        this.offerService = apartamentService;
    }

    @PostMapping("/api/offer")
    public ResponseEntity addOffer(@RequestBody OfferRequest apartamentRequest) {
        offerService.addOffer(apartamentRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
}
