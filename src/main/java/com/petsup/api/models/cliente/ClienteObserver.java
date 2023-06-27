package com.petsup.api.models.cliente;

import org.springframework.mail.javamail.JavaMailSender;

public interface ClienteObserver {
    public void atualiza(JavaMailSender enviador, String remetente, String destinatario, double preco);
}
