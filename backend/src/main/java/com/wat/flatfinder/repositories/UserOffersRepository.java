package com.wat.flatfinder.repositories;

import com.wat.flatfinder.entities.UserOffers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserOffersRepository extends CrudRepository<UserOffers, Integer> {
    Optional<UserOffers> findByUserIdAndOfferId(int user_id, int offer_id);

}
