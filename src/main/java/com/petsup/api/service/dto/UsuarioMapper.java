package com.petsup.api.service.dto;

import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;

public class UsuarioMapper {

    public static Usuario ofCliente(UsuarioClienteDto usuarioCriacaoDto) {
        UsuarioCliente usuario = new UsuarioCliente();

        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setSenha(usuarioCriacaoDto.getSenha());
        usuario.setCep(usuarioCriacaoDto.getCep());
        usuario.setTelefone(usuarioCriacaoDto.getTelefone());
        usuario.setCPF(usuarioCriacaoDto.getCPF());

        return usuario;
    }

    public static Usuario ofPetshop(UsuarioPetshopDto usuarioCriacaoDto) {
        UsuarioPetshop usuario = new UsuarioPetshop();

        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setSenha(usuarioCriacaoDto.getSenha());
        usuario.setEndereco(usuarioCriacaoDto.getEndereco());
        usuario.setTelefone(usuarioCriacaoDto.getTelefone());
        usuario.setCNPJ(usuarioCriacaoDto.getCNPJ());
        usuario.setRazaoSocial(usuarioCriacaoDto.getRazaoSocial());

        return usuario;
    }
}
