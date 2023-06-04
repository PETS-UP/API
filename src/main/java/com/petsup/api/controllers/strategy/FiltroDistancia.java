package com.petsup.api.controllers.strategy;

import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.PetshopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

    public class FiltroDistancia implements FiltroStrategy{
    @Autowired
    PetshopRepository petshopRepository;
    @Override
    public List<UsuarioPetshop> ordenar(){
        return null;
    }
}
