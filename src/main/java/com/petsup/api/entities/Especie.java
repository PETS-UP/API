package com.petsup.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public enum Especie {
    CACHORRO,
    GATO,
    COELHO,
    ROEDOR
}
