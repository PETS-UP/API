package com.petsup.api.repositories;

import com.petsup.api.entities.ClientePetshopSubscriber;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientePetshopSubscriberRepository {
    Optional<ClientePetshopSubscriber> findByFkPetshopId (Integer idPetshop);
}
