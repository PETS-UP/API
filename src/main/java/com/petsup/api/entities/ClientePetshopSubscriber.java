package com.petsup.api.entities;

import com.petsup.api.entities.usuario.ClienteObserver;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.service.dto.UsuarioClienteDto;
import jakarta.persistence.*;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Entity
public class ClientePetshopSubscriber /*implements ClienteObserver*/ {

    @Id
    private int id;
    @ManyToOne
    UsuarioCliente fkCliente; // inscritos

    @ManyToOne
    UsuarioPetshop fkPetshop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioCliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(UsuarioCliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    public UsuarioPetshop getFkPetshop() {
        return fkPetshop;
    }

    public void setFkPetshop(UsuarioPetshop fkPetshop) {
        this.fkPetshop = fkPetshop;
    }

    // Observer
    public void notifica(JavaMailSender enviador, String emailPetshop, String emailCliente, double preco) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(emailPetshop);
        email.setTo(emailCliente);
        email.setSubject("Desconto");
        email.setText("Novo preço" + preco);

        enviador.send(email);

        System.out.println("Mensagem enviada!");

    }
}
