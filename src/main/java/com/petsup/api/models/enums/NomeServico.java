package com.petsup.api.models.enums;

public enum NomeServico {
    BANHO("Banho"),
    TOSA("Tosa"),
    BANHO_E_TOSA("Banho e Tosa");

    private String nomeServico;

    private NomeServico(String servico) {
        this.nomeServico = servico;
    }
}
