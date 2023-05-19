package com.petsup.api.entities.usuario;

import com.petsup.api.service.dto.UsuarioClienteDto;
import org.springframework.mail.javamail.JavaMailSender;

public interface ClienteObserver {
    public void atualiza(JavaMailSender enviador, String remetente, String destinatario, double preco);
}
