package com.petsup.api.mapper;

import com.petsup.api.dto.authentication.ClienteTokenDto;
import com.petsup.api.dto.cliente.ClienteDto;
import com.petsup.api.models.cliente.Cliente;

import java.util.List;
import java.util.Objects;

public class ClienteMapper {

    public static Cliente ofCliente(ClienteDto clienteCriacaoDto) {
        Cliente cliente = new Cliente();

        cliente.setNome(clienteCriacaoDto.getNome());
        cliente.setEmail(clienteCriacaoDto.getEmail());
        cliente.setSenha(clienteCriacaoDto.getSenha());
        cliente.setCep(clienteCriacaoDto.getCep());
        cliente.setTelefone(clienteCriacaoDto.getTelefone());
        cliente.setCpf(clienteCriacaoDto.getCpf());
        cliente.setEstado(clienteCriacaoDto.getEstado());
        cliente.setCidade(clienteCriacaoDto.getCidade());
        cliente.setBairro(clienteCriacaoDto.getBairro());
        cliente.setRua(clienteCriacaoDto.getRua());
        cliente.setNumero(clienteCriacaoDto.getNumero());
        cliente.setImagemPerfil("https://petsupstorage.blob.core.windows.net/imagesstorage/ICON-PROFILE.png");

        return cliente;
    }

    public static ClienteTokenDto ofCliente(Cliente cliente, String token) {
        ClienteTokenDto clienteTokenDto = new ClienteTokenDto();

        clienteTokenDto.setUserId(cliente.getId());
        clienteTokenDto.setEmail(cliente.getEmail());
        clienteTokenDto.setNome(cliente.getNome());
        clienteTokenDto.setToken(token);

        return clienteTokenDto;
    }

    public static ClienteDto ofClienteDto(Cliente cliente) {
        ClienteDto clienteDto = new ClienteDto();

        clienteDto.setNome(cliente.getNome());
        clienteDto.setEmail(cliente.getEmail());
        clienteDto.setCpf(cliente.getCpf());
        clienteDto.setDataNasc(cliente.getDataNasc());
        clienteDto.setTelefone(cliente.getTelefone());
        clienteDto.setCep(cliente.getCep());
        clienteDto.setEstado(cliente.getEstado());
        clienteDto.setCidade(cliente.getCidade());
        clienteDto.setBairro(cliente.getBairro());
        clienteDto.setRua(cliente.getRua());
        clienteDto.setNumero(cliente.getNumero());
        clienteDto.setImagemPerfil(!Objects.equals(cliente.getImagemPerfil(), "https://petsupstorage.blob.core.windows.net/imagesstorage/ICON-PROFILE.png")
                ? "https://petsupstorage.blob.core.windows.net/imagesstorage/" + cliente.getImagemPerfil()
                : cliente.getImagemPerfil());

        return clienteDto;
    }

    public static Cliente ofCliente(ClienteDto clienteAtualizacaoDto, Cliente cliente) {
        Cliente clienteAtt = new Cliente();

        clienteAtt.setId(cliente.getId());
        clienteAtt.setNome(clienteAtualizacaoDto.getNome());
        clienteAtt.setEmail(clienteAtualizacaoDto.getEmail());
        clienteAtt.setSenha(cliente.getSenha());
        clienteAtt.setCep(clienteAtualizacaoDto.getCep());
        clienteAtt.setTelefone(clienteAtualizacaoDto.getTelefone());
        clienteAtt.setCpf(clienteAtualizacaoDto.getCpf());
        clienteAtt.setEstado(clienteAtualizacaoDto.getEstado());
        clienteAtt.setCidade(clienteAtualizacaoDto.getCidade());
        clienteAtt.setBairro(clienteAtualizacaoDto.getBairro());
        clienteAtt.setRua(clienteAtualizacaoDto.getRua());
        clienteAtt.setNumero(clienteAtualizacaoDto.getNumero());

        return clienteAtt;
    }

    public static Cliente ofCliente(double latitude, double longitude,
                                    Cliente cliente){
        Cliente usuario = new Cliente();

        usuario.setId(cliente.getId());
        usuario.setNome(cliente.getNome());
        usuario.setEmail(cliente.getEmail());
        usuario.setSenha(cliente.getSenha());
        usuario.setCep(cliente.getCep());
        usuario.setTelefone(cliente.getTelefone());
        usuario.setCpf(cliente.getCpf());
        usuario.setEstado(cliente.getEstado());
        usuario.setCidade(cliente.getCidade());
        usuario.setBairro(cliente.getBairro());
        usuario.setRua(cliente.getRua());
        usuario.setNumero(cliente.getNumero());
        usuario.setImagemPerfil(cliente.getImagemPerfil());
        usuario.setLatitude(latitude);
        usuario.setLongitude(longitude);

        return usuario;
    }

    public static List<ClienteDto> ofListUsuarioClienteDto(List<Cliente> usuarios){
        List<ClienteDto> clienteDtos = usuarios.stream().map(
                ClienteMapper::ofClienteDto).toList();

        return clienteDtos;
    }
}
