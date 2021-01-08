package com.wat.flatfinder.repositories;

import com.wat.flatfinder.entities.UserOffers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOffersRepository extends CrudRepository<UserOffers, Integer> {
}
