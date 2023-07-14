package com.petsup.api.repositories.cliente;

import com.petsup.api.models.cliente.ClienteSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteSubscriberRepository extends JpaRepository<ClienteSubscriber, Integer> {
    Optional<ClienteSubscriber> findByFkPetshopId (Integer idPetshop);
}
