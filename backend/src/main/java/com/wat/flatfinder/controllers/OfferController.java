package com.wat.flatfinder.controllers;

import com.wat.flatfinder.dtos.OfferRequest;
import com.wat.flatfinder.dtos.UserResponse;
import com.wat.flatfinder.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OfferController {
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService apartamentService) {
        this.offerService = apartamentService;
    }

//    @CrossOrigin(origins = "http://localhost:3000")

    @PostMapping(value="/api/offers", consumes="application/json;charset=UTF-8")
    public ResponseEntity addOffer(@RequestBody OfferRequest apartamentRequest ) {
        try{
            offerService.addOffer(apartamentRequest);
            System.out.println(apartamentRequest.getTitle());
            return new ResponseEntity(HttpStatus.OK);
        }catch (IllegalArgumentException ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/offers")
    public ResponseEntity<List<UserResponse>> getAll() {
        return new ResponseEntity(offerService.getAll(), HttpStatus.OK);
    }
}
