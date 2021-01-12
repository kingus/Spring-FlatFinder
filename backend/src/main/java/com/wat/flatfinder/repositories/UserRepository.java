package com.wat.flatfinder.repositories;

import com.wat.flatfinder.entities.Offer;
import com.wat.flatfinder.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
