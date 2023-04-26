package com.petsup.api.repositories;

import com.petsup.api.entities.ClientePetshopSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientePetshopSubscriberRepository extends JpaRepository<ClientePetshopSubscriber, Integer> {
    Optional<ClientePetshopSubscriber> findByFkPetshopId (Integer idPetshop);
}
