package com.wat.flatfinder.controllers;

import com.wat.flatfinder.dtos.OfferRequest;
import com.wat.flatfinder.dtos.UserRequest;
import com.wat.flatfinder.services.OfferService;
import com.wat.flatfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public ResponseEntity addUser(@RequestBody UserRequest userRequest) {
        try{
            userService.addUser(userRequest);
            return new ResponseEntity(HttpStatus.OK);

        } catch (Exception exception){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") int id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
}
