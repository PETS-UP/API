package com.petsup.api.dto;

import java.time.LocalTime;

public record HorariosDTO(LocalTime horaAbertura, LocalTime horaFechamento) {
}
