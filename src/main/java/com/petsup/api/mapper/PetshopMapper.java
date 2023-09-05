package com.petsup.api.mapper;

import com.petsup.api.dto.petshop.PetshopDto;
import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.dto.authentication.PetshopTokenDto;

import java.util.List;

public class PetshopMapper {

    public static Petshop ofPetshop(PetshopDto petshopCriacaoDto) {
        Petshop petshop = new Petshop();

        petshop.setNome(petshopCriacaoDto.getNome());
        petshop.setEmail(petshopCriacaoDto.getEmail());
        petshop.setSenha(petshopCriacaoDto.getSenha());
        petshop.setCep(petshopCriacaoDto.getCep());
        petshop.setTelefone(petshopCriacaoDto.getTelefone());
        petshop.setCep(petshopCriacaoDto.getCep());
        petshop.setCnpj(petshopCriacaoDto.getCnpj());
        petshop.setRazaoSocial(petshopCriacaoDto.getRazaoSocial());
        petshop.setEstado(petshopCriacaoDto.getEstado());
        petshop.setCidade(petshopCriacaoDto.getCidade());
        petshop.setBairro(petshopCriacaoDto.getBairro());
        petshop.setRua(petshopCriacaoDto.getRua());
        petshop.setNumero(petshopCriacaoDto.getNumero());
        petshop.setHoraAbertura(petshopCriacaoDto.getHoraAbertura());
        petshop.setHoraFechamento(petshopCriacaoDto.getHoraFechamento());
        petshop.setDiasFuncionais(petshopCriacaoDto.getDiasFuncionais());

        return petshop;
    }

    public static PetshopTokenDto ofPetshop(Petshop petshop, String token) {
        PetshopTokenDto petshopTokenDto = new PetshopTokenDto();

        petshopTokenDto.setUserId(petshop.getId());
        petshopTokenDto.setEmail(petshop.getEmail());
        petshopTokenDto.setNome(petshop.getNome());
        petshopTokenDto.setToken(token);

        return petshopTokenDto;
    }

    public static PetshopDto ofPetshopDto(Petshop petshop) {
        PetshopDto petshopDto = new PetshopDto();

        petshopDto.setId(petshop.getId());
        petshopDto.setNome(petshop.getNome());
        petshopDto.setEmail(petshop.getEmail());
        petshopDto.setTelefone(petshop.getTelefone());
        petshopDto.setRazaoSocial(petshop.getRazaoSocial());
        petshopDto.setCnpj(petshop.getCnpj());
        petshopDto.setCep(petshop.getCep());
        petshopDto.setEstado(petshop.getEstado());
        petshopDto.setCidade(petshop.getCidade());
        petshopDto.setBairro(petshop.getBairro());
        petshopDto.setRua(petshop.getRua());
        petshopDto.setNumero(petshop.getNumero());
        petshopDto.setHoraAbertura(petshop.getHoraAbertura());
        petshopDto.setHoraFechamento(petshop.getHoraFechamento());
        petshopDto.setDiasFuncionais(petshop.getDiasFuncionais());

        return petshopDto;
    }

    public static Petshop ofPetshop(PetshopDto petshopAtualizacaoDto, Petshop petshop) {
        Petshop petshopAtt = new Petshop();

        petshopAtt.setId(petshop.getId());
        petshopAtt.setNome(petshopAtualizacaoDto.getNome());
        petshopAtt.setEmail(petshopAtualizacaoDto.getEmail());
        petshopAtt.setSenha(petshop.getSenha());
        petshopAtt.setCep(petshopAtualizacaoDto.getCep());
        petshopAtt.setTelefone(petshopAtualizacaoDto.getTelefone());
        petshopAtt.setCnpj(petshopAtualizacaoDto.getCnpj());
        petshopAtt.setEstado(petshopAtualizacaoDto.getEstado());
        petshopAtt.setCidade(petshopAtualizacaoDto.getCidade());
        petshopAtt.setBairro(petshopAtualizacaoDto.getBairro());
        petshopAtt.setRua(petshopAtualizacaoDto.getRua());
        petshopAtt.setNumero(petshopAtualizacaoDto.getNumero());
        petshopAtt.setHoraAbertura(petshopAtualizacaoDto.getHoraAbertura());
        petshopAtt.setHoraFechamento(petshopAtualizacaoDto.getHoraFechamento());
        petshopAtt.setDiasFuncionais(petshopAtualizacaoDto.getDiasFuncionais());

        return petshopAtt;
    }

    public static List<PetshopDto> ofListUsuarioPetshopDto(List<Petshop> petshops){
        List<PetshopDto> petshopDtos = petshops.stream().map(
                PetshopMapper::ofPetshopDto).toList();

        return petshopDtos;
    }
}
