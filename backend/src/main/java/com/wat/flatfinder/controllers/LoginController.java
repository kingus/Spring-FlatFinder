package com.wat.flatfinder.controllers;

import com.wat.flatfinder.dtos.LoginRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){
    }
}
