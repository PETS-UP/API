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
        usuario.setCpf(usuarioCriacaoDto.getCpf());

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
        usuario.setCnpj(usuarioCriacaoDto.getCnpj());
        usuario.setRazaoSocial(usuarioCriacaoDto.getRazaoSocial());

        return usuario;
    }

    public static UsuarioClienteDto ofClienteDto(UsuarioCliente usuarioCliente) {
        UsuarioClienteDto usuarioClienteDto = new UsuarioClienteDto();

        usuarioClienteDto.setNome(usuarioCliente.getNome());
        usuarioClienteDto.setEmail(usuarioCliente.getEmail());
        usuarioClienteDto.setDataNasc(usuarioCliente.getDataNasc());
        usuarioClienteDto.setTelefone(usuarioCliente.getTelefone());
        usuarioClienteDto.setCep(usuarioCliente.getCep());
        usuarioClienteDto.setEstado(usuarioCliente.getEstado());
        usuarioClienteDto.setCidade(usuarioCliente.getCidade());
        usuarioClienteDto.setBairro(usuarioCliente.getBairro());
        usuarioClienteDto.setRua(usuarioCliente.getRua());
        usuarioClienteDto.setNumero(usuarioCliente.getNumero());

        return usuarioClienteDto;
    }

    public static UsuarioPetshopDto ofPetshopDto(UsuarioPetshop usuarioPetshop) {
        UsuarioPetshopDto usuarioPetshopDto = new UsuarioPetshopDto();

        usuarioPetshopDto.setNome(usuarioPetshop.getNome());
        usuarioPetshopDto.setEmail(usuarioPetshop.getEmail());
        usuarioPetshopDto.setTelefone(usuarioPetshop.getTelefone());
        usuarioPetshopDto.setRazaoSocial(usuarioPetshop.getRazaoSocial());
        usuarioPetshopDto.setCep(usuarioPetshop.getCep());
        usuarioPetshopDto.setEstado(usuarioPetshop.getEstado());
        usuarioPetshopDto.setCidade(usuarioPetshop.getCidade());
        usuarioPetshopDto.setBairro(usuarioPetshopDto.getBairro());
        usuarioPetshopDto.setRua(usuarioPetshop.getRua());
        usuarioPetshopDto.setNumero(usuarioPetshop.getNumero());

        return usuarioPetshopDto;
    }
}
