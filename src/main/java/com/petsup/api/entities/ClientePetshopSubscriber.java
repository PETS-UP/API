package com.petsup.api.entities;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import jakarta.persistence.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Entity
public class ClientePetshopSubscriber /*implements ClienteObserver*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        email.setSubject("Atualização de Preço");
        email.setText(String.format("Olá, %s, temos uma novidade pra você!\n\n" +
                "A %s está com novos preços, que tal dar uma conferida?\n\n" +
                "Corre pra aproveitar a promoção!",
                fkCliente.getNome(), fkPetshop.getNome()));

        enviador.send(email);

        System.out.println("Mensagem enviada!");

    }
}
