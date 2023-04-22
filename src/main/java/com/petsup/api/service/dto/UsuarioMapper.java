package com.petsup.api.service.dto;

import com.petsup.api.entities.usuario.Usuario;

public class UsuarioMapper {

    public static Usuario of(UsuarioDto usuarioCriacaoDto) {
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setSenha(usuarioCriacaoDto.getSenha());
        usuario.setEndereco(usuarioCriacaoDto.getEndereco());
        usuario.setTelefone(usuarioCriacaoDto.getTelefone());

        return usuario;
    }
}
