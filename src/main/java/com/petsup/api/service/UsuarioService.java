package com.petsup.api.service;

import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.repositories.UsuarioRepository;
import com.petsup.api.service.dto.UsuarioDto;
import com.petsup.api.service.dto.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void criar(UsuarioDto usuarioDto){
        final Usuario novoUsuario = UsuarioMapper.of(usuarioDto);
        this.usuarioRepository.save(novoUsuario);
    }
}
