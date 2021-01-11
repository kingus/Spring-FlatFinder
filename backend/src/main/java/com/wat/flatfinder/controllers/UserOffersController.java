package com.wat.flatfinder.controllers;

import com.wat.flatfinder.dtos.UpdateNoteRequest;
import com.wat.flatfinder.dtos.UserOffersRequest;
import com.wat.flatfinder.dtos.UserRequest;
import com.wat.flatfinder.services.UserOffersService;
import com.wat.flatfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserOffersController {
    private final UserOffersService userOffersService;

    @Autowired
    public UserOffersController(UserOffersService userOffersService) {
        this.userOffersService = userOffersService;
    }

    @PostMapping("/api/user-offers")
    @CrossOrigin(origins = "http://localhost:3000")

    public ResponseEntity addUserOffers(@RequestBody UserOffersRequest userOffersRequest, @AuthenticationPrincipal String username) {
        System.out.println("CONTROLLER" + userOffersRequest.getOffer_id());

        try{
            userOffersService.addUserOffers(userOffersRequest, username);
            return new ResponseEntity(HttpStatus.OK);

        } catch (Exception exception){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/api/user-offers/{id}")
    @GetMapping("/api/user-offers")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity getUserOffers(@AuthenticationPrincipal String username) {
    //    public ResponseEntity getUserOffers(@PathVariable(value = "id") @AuthenticationPrincipal String username) {
            System.out.println(username);

            try{
                return new ResponseEntity(userOffersService.getAll(username),HttpStatus.OK);

            } catch (Exception exception){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }

    @DeleteMapping("/api/user-offers/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity deleteUserOffer(@AuthenticationPrincipal String username, @PathVariable(value = "id") int id) {
            System.out.println(username);

            try{
                userOffersService.deleteUserOffer(id,username);
                return new ResponseEntity(HttpStatus.OK);

            } catch (Exception exception){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }

    @PatchMapping("/api/user-offers/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity updateUserOfferNote(@AuthenticationPrincipal String username, @PathVariable(value = "id") int id, @RequestBody UpdateNoteRequest updateNoteRequest)  {
            System.out.println(username);

            try{
                userOffersService.updateUserOfferNote(id,username, updateNoteRequest);
                return new ResponseEntity(HttpStatus.OK);

            } catch (Exception exception){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
}
