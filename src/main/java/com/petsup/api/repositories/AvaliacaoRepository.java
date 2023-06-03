package com.petsup.api.repositories;

import com.petsup.api.entities.AvaliacaoPetshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoPetshop, Integer> {

}
