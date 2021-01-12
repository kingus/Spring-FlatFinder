package com.wat.flatfinder.services;

import com.wat.flatfinder.dtos.AuthorityRequest;
import com.wat.flatfinder.entities.Authority;
import com.wat.flatfinder.repositories.AuthorityRepository;
import com.wat.flatfinder.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthorityServiceImpl implements AuthorityService{

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void addAuthority(AuthorityRequest authorityRequest) {
        Authority authority = new Authority();
        authority.setAuthority(authorityRequest.getAuthority());
        authority.setUsername(authorityRequest.getUsername());

        authorityRepository.save(authority);
    }
}
