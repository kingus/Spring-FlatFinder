package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.UserRequest;

public interface UserService {
    void addUser(UserRequest userRequest);
    void deleteUser(int id);
}
