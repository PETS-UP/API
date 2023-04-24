package com.petsup.api.service.dto;

import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.service.autentication.dto.ClienteTokenDto;
import com.petsup.api.service.autentication.dto.PetshopTokenDto;

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
        usuario.setCep(usuarioCriacaoDto.getCep());
        usuario.setTelefone(usuarioCriacaoDto.getTelefone());
        usuario.setCep(usuarioCriacaoDto.getCep());
        usuario.setCNPJ(usuarioCriacaoDto.getCNPJ());
        usuario.setRazaoSocial(usuarioCriacaoDto.getRazaoSocial());

        return usuario;
    }

    public static ClienteTokenDto ofCliente(UsuarioCliente cliente, String token) {
        ClienteTokenDto usuarioTokenDto = new ClienteTokenDto();

        usuarioTokenDto.setUserId(cliente.getId());
        usuarioTokenDto.setEmail(cliente.getEmail());
        usuarioTokenDto.setNome(cliente.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }

    public static PetshopTokenDto ofPetshop(UsuarioPetshop petshop, String token) {
        PetshopTokenDto usuarioTokenDto = new PetshopTokenDto();

        usuarioTokenDto.setUserId(petshop.getId());
        usuarioTokenDto.setEmail(petshop.getEmail());
        usuarioTokenDto.setNome(petshop.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }
}
