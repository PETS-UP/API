package com.petsup.api.repositories;

import com.petsup.api.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.event.ListDataListener;
import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    List<Servico> findByFkPetshopId(int fkPetshop);
}
