package com.petsup.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public enum Servico {
    BANHO,
    TOSA,
    BANHOTOSA;
}
