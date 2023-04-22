package com.petsup.api.service;

import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.UsuarioRepository;
import com.petsup.api.service.dto.UsuarioClienteDto;
import com.petsup.api.service.dto.UsuarioDto;
import com.petsup.api.service.dto.UsuarioMapper;
import com.petsup.api.service.dto.UsuarioPetshopDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void criarCliente(UsuarioClienteDto usuarioDto){
        final Usuario novoUsuario = UsuarioMapper.ofCliente(usuarioDto);
        this.usuarioRepository.save(novoUsuario);
    }

    public void criarPetshop(UsuarioPetshopDto usuarioDto){
        final Usuario novoUsuario = UsuarioMapper.ofPetshop(usuarioDto);
        this.usuarioRepository.save(novoUsuario);
    }
}
