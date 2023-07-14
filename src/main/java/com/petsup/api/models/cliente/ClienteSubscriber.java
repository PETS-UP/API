package com.petsup.api.models.cliente;

import com.petsup.api.models.petshop.Petshop;
import jakarta.persistence.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Entity
public class ClienteSubscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    Cliente fkCliente; // inscritos

    @ManyToOne
    Petshop fkPetshop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    public Petshop getFkPetshop() {
        return fkPetshop;
    }

    public void setFkPetshop(Petshop fkPetshop) {
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
