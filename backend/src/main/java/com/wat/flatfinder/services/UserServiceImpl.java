package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.UserRequest;
import com.wat.flatfinder.dtos.UserResponse;
import com.wat.flatfinder.entities.User;
import com.wat.flatfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(UserRequest userRequest) {
    User user = new User();
    user.setUsername(userRequest.getUsername());
    user.setEmail(userRequest.getEmail());
    user.setPrefferedDistrict(userRequest.getPreffered_district());
    user.setCreationDate(LocalDateTime.now());
    userRepository.save(user);

    }
    public void deleteUser(int id) {
        //does not work
        System.out.println(id);
        for (User user:userRepository.findAll()
             ) {

            System.out.println(user);
        }

        System.out.println(userRepository.findById(id).isPresent());

        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public UserResponse getUser(int id) {
        System.out.println(id);
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
//            UserResponse userResponse =
            return new UserResponse(user.get().getUsername(), user.get().getEmail(), user.get().getPrefferedDistrict());
//            ;
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
