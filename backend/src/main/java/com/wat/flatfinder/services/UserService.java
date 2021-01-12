package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.UserRequest;
import com.wat.flatfinder.dtos.UserResponse;

public interface UserService {
    void addUser(UserRequest userRequest);
    void deleteUser(int id);
    UserResponse getUser(String username);

    }
