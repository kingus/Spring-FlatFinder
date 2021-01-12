package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.AuthorityRequest;
import com.wat.flatfinder.dtos.UserRequest;
import com.wat.flatfinder.dtos.UserResponse;
import com.wat.flatfinder.entities.User;
import com.wat.flatfinder.repositories.AuthorityRepository;
import com.wat.flatfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityService authorityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
    }

    @Override
    public void addUser(UserRequest userRequest) {
    User user = new User();
    user.setUsername(userRequest.getUsername());
    String encodedPassword = new BCryptPasswordEncoder().encode(userRequest.getPassword());

    user.setEmail(userRequest.getEmail());
    user.setPassword(encodedPassword);
    user.setEnabled(true);
    user.setPrefferedDistrict(userRequest.getPreffered_district());
    user.setCreationDate(LocalDateTime.now());

    System.out.println(encodedPassword);
    userRepository.save(user);
    authorityService.addAuthority(new AuthorityRequest(user.getUsername(), "ROLE_ADMIN"));


    }
    public void deleteUser(int id) {

        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public UserResponse getUser(String username) {
        System.out.println(username);
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return new UserResponse(user.get().getUsername(), user.get().getEmail(), user.get().getPrefferedDistrict());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
