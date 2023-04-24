package com.petsup.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public class UsuarioClienteDto extends UsuarioDto{

    @Schema(description = "Data de nascimento do cliente", example = "1999-01-01")
    @Past
    private LocalDate dataNasc;

    @Schema(name = "CPF", description = "CPF do cliente", example = "12345678901")
    @CPF
    private String cpf;

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
