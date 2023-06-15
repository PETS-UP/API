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
        usuario.setCpf(usuarioCriacaoDto.getCpf());
        usuario.setEstado(usuarioCriacaoDto.getEstado());
        usuario.setCidade(usuarioCriacaoDto.getCidade());
        usuario.setBairro(usuarioCriacaoDto.getBairro());
        usuario.setRua(usuarioCriacaoDto.getRua());
        usuario.setNumero(usuarioCriacaoDto.getNumero());

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
        usuario.setEstado(usuarioCriacaoDto.getEstado());
        usuario.setCidade(usuarioCriacaoDto.getCidade());
        usuario.setBairro(usuarioCriacaoDto.getBairro());
        usuario.setRua(usuarioCriacaoDto.getRua());
        usuario.setNumero(usuarioCriacaoDto.getNumero());

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

        usuarioPetshopDto.setId(usuarioPetshop.getId());
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

    public static UsuarioCliente ofCliente(UsuarioClienteDto usuarioAtualizacaoDto, UsuarioCliente usuarioCliente) {
        UsuarioCliente usuario = new UsuarioCliente();

        usuario.setId(usuarioCliente.getId());
        usuario.setNome(usuarioAtualizacaoDto.getNome());
        usuario.setEmail(usuarioAtualizacaoDto.getEmail());
        usuario.setSenha(usuarioCliente.getSenha());
        usuario.setCep(usuarioAtualizacaoDto.getCep());
        usuario.setTelefone(usuarioAtualizacaoDto.getTelefone());
        usuario.setCpf(usuarioAtualizacaoDto.getCpf());
        usuario.setEstado(usuarioAtualizacaoDto.getEstado());
        usuario.setCidade(usuarioAtualizacaoDto.getCidade());
        usuario.setBairro(usuarioAtualizacaoDto.getBairro());
        usuario.setRua(usuarioAtualizacaoDto.getRua());
        usuario.setNumero(usuarioCliente.getNumero());

        return usuario;
    }

    public static UsuarioCliente ofCliente(double latitude, double longitude,
                                           UsuarioCliente usuarioCliente){
        UsuarioCliente usuario = new UsuarioCliente();

        usuario.setId(usuarioCliente.getId());
        usuario.setNome(usuarioCliente.getNome());
        usuario.setEmail(usuarioCliente.getEmail());
        usuario.setSenha(usuarioCliente.getSenha());
        usuario.setCep(usuarioCliente.getCep());
        usuario.setTelefone(usuarioCliente.getTelefone());
        usuario.setCpf(usuarioCliente.getCpf());
        usuario.setEstado(usuarioCliente.getEstado());
        usuario.setCidade(usuarioCliente.getCidade());
        usuario.setBairro(usuarioCliente.getBairro());
        usuario.setRua(usuarioCliente.getRua());
        usuario.setNumero(usuarioCliente.getNumero());
        usuario.setLatitude(latitude);
        usuario.setLongitude(longitude);

        return usuario;
    }
}
