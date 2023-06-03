package com.petsup.api.controllers.strategy;

import com.petsup.api.entities.usuario.UsuarioPetshop;

import java.util.List;

public interface FiltroStrategy {
    List<UsuarioPetshop> ordenar();
}
