package com.wat.flatfinder.repositories;

import com.wat.flatfinder.entities.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Integer> {
    public Optional<Offer> findBySourceId(String id);
}