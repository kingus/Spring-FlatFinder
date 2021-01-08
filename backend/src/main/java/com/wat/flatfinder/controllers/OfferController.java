package com.wat.flatfinder.controllers;

import com.wat.flatfinder.dtos.OfferRequest;
import com.wat.flatfinder.dtos.UserResponse;
import com.wat.flatfinder.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OfferController {
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService apartamentService) {
        this.offerService = apartamentService;
    }

    @PostMapping("/api/offers")
    public ResponseEntity addOffer(@RequestBody OfferRequest apartamentRequest) {
        offerService.addOffer(apartamentRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/offers")
    public ResponseEntity<List<UserResponse>> getAll() {
        return new ResponseEntity(offerService.getAll(), HttpStatus.OK);
    }
}
