package com.wat.flatfinder.controllers;

import com.wat.flatfinder.dtos.UserOffersRequest;
import com.wat.flatfinder.dtos.UserRequest;
import com.wat.flatfinder.services.UserOffersService;
import com.wat.flatfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserOffersController {
    private final UserOffersService userOffersService;

    @Autowired
    public UserOffersController(UserOffersService userOffersService) {
        this.userOffersService = userOffersService;
    }

    @PostMapping("/api/user-offers")
    public ResponseEntity addUserOffers(@RequestBody UserOffersRequest userOffersRequest) {

        try{
            userOffersService.addUserOffers(userOffersRequest);
            return new ResponseEntity(HttpStatus.OK);

        } catch (Exception exception){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/user-offers/{id}")
    public ResponseEntity getUserOffers(@PathVariable(value = "id") int id) {

        try{
            return new ResponseEntity(userOffersService.getAll(id),HttpStatus.OK);

        } catch (Exception exception){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
