package com.petsup.api.dto;

import java.time.LocalTime;

public record HorariosDto(LocalTime horaAbertura, LocalTime horaFechamento) {
}
