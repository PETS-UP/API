package com.petsup.api.mapper;

import com.petsup.api.dto.petshop.PetshopAbertoDto;
import com.petsup.api.dto.petshop.PetshopAvaliacaoDto;
import com.petsup.api.dto.petshop.PetshopDto;
import com.petsup.api.dto.petshop.PetshopExibicaoDto;
import com.petsup.api.models.petshop.Petshop;
import com.petsup.api.dto.authentication.PetshopTokenDto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
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
        petshop.setHoraAbertura(LocalTime.of(0, 0));
        petshop.setHoraFechamento(LocalTime.of(0, 0));
        petshop.setDiasFuncionais(new ArrayList<>());
        petshop.setImagemPerfil("https://petsupstorage.blob.core.windows.net/imagesstorage/ICON-PETSHOP.png");

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

    public static PetshopAbertoDto ofPetshopAbertoDto(Petshop petshop, Boolean estaAberto) {
        PetshopAbertoDto petshopAbertoDto = new PetshopAbertoDto();

        petshopAbertoDto.setId(petshop.getId());
        petshopAbertoDto.setEstaAberto(estaAberto);
        petshopAbertoDto.setNome(petshop.getNome());

        return petshopAbertoDto;
    }

    public static PetshopExibicaoDto ofPetshopExibicaoDto(Petshop petshop, PetshopAvaliacaoDto petshopAvaliacaoDto) {
        PetshopExibicaoDto petshopExibicaoDto = new PetshopExibicaoDto();

        petshopExibicaoDto.setId(petshop.getId());
        petshopExibicaoDto.setNome(petshop.getNome());
        petshopExibicaoDto.setEmail(petshop.getEmail());
        petshopExibicaoDto.setSenha(petshop.getSenha());
        petshopExibicaoDto.setCep(petshop.getCep());
        petshopExibicaoDto.setTelefone(petshop.getTelefone());
        petshopExibicaoDto.setCnpj(petshop.getCnpj());
        petshopExibicaoDto.setEstado(petshop.getEstado());
        petshopExibicaoDto.setCidade(petshop.getCidade());
        petshopExibicaoDto.setBairro(petshop.getBairro());
        petshopExibicaoDto.setRua(petshop.getRua());
        petshopExibicaoDto.setNumero(petshop.getNumero());
        petshopExibicaoDto.setHoraAbertura(petshop.getHoraAbertura() != null ? petshop.getHoraAbertura() : LocalTime.of(0, 0));
        petshopExibicaoDto.setHoraFechamento(petshop.getHoraFechamento() != null ? petshop.getHoraFechamento() : LocalTime.of(0, 0));
        petshopExibicaoDto.setDiasFuncionais(petshop.getDiasFuncionais());
        petshopExibicaoDto.setNota(petshopAvaliacaoDto.getId() == petshop.getId() ? petshopAvaliacaoDto.getNota() : 0.0);
        petshopExibicaoDto.setOpen(LocalTime.now().isAfter(petshopExibicaoDto.getHoraAbertura()) && LocalTime.now().isBefore(petshopExibicaoDto.getHoraFechamento()));
        petshopExibicaoDto.setImagemPerfil(
                petshop.getImagemPerfil() != "https://petsupstorage.blob.core.windows.net/imagesstorage/ICON-PETSHOP.png"
                        ? petshop.getImagemPerfil() : "https://petsupstorage.blob.core.windows.net/imagesstorage/ICON-PETSHOP.png"
        );
        return petshopExibicaoDto;
    }

    public static List<PetshopExibicaoDto> ofListPetshopExibicaoDto(List<Petshop> petshops, List<PetshopAvaliacaoDto> petshopAvaliacaoDtos) {

        List<PetshopExibicaoDto> petshopExibicaoDtos = new ArrayList<>();

        for (Petshop petshop : petshops) {
            PetshopAvaliacaoDto matchingAvaliacaoDto = petshopAvaliacaoDtos.stream()
                    .filter(dto -> dto.getId().equals(petshop.getId()))
                    .findFirst()
                    .orElse(new PetshopAvaliacaoDto());

            PetshopExibicaoDto exibicaoDto = ofPetshopExibicaoDto(petshop, matchingAvaliacaoDto);
            petshopExibicaoDtos.add(exibicaoDto);
        }

        return petshopExibicaoDtos;
    }

    public static List<PetshopDto> ofListUsuarioPetshopDto(List<Petshop> petshops){
        List<PetshopDto> petshopDtos = petshops.stream().map(
                PetshopMapper::ofPetshopDto).toList();

        return petshopDtos;
    }
}
