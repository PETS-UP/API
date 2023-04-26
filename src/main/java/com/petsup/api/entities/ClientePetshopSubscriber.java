package com.petsup.api.entities;

import com.petsup.api.entities.usuario.ClienteObserver;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import jakarta.persistence.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Entity
public class ClientePetshopSubscriber implements ClienteObserver {

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
//    public void inscricao(UsuarioCliente listener){
//        fkPetshops.getInscritos().add(listener);
//    }
//
//    public void desinscricao(UsuarioCliente listener){
//        fkCliente.remove(listener);
//    }
//
//    public void notifica(String emailRemetente, String emailDestinatario, String host){
//        fkCliente.forEach(listener -> listener.atualiza(emailRemetente, emailDestinatario));
//    }

    @Override
    public void atualiza(String emailRemetente, String emailDestinatario) {
        // Recipient's email ID needs to be mentioned.
        // String to = "fromaddress@gmail.com";

        // Sender's email ID needs to be mentioned
        // String from = "toaddress@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(emailRemetente, "*******");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(emailRemetente));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Send the actual HTML message.
            message.setContent(
                    "<h1>Nova promoção!</h1>",
                    "text/html");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
