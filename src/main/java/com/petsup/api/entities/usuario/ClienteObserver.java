package com.petsup.api.entities.usuario;

import com.petsup.api.service.dto.UsuarioClienteDto;

public interface ClienteObserver {
    public void atualiza(String email, double preco);
}
