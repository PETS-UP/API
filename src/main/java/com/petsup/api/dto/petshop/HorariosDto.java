package com.petsup.api.dto.petshop;

import java.time.LocalTime;

public record HorariosDto(LocalTime horaAbertura, LocalTime horaFechamento) {
}
