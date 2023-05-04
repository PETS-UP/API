package com.petsup.api.entities.usuario;

import com.petsup.api.entities.*;
import com.petsup.api.service.dto.UsuarioClienteDto;
import jakarta.persistence.*;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Entity
@Table(name = "Cliente")
public class UsuarioCliente extends Usuario /*implements ClienteObserver*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "fkCliente")
    private List<AvaliacaoPetshop> avaliacoes;

    @OneToMany(mappedBy = "fkCliente")
    private List<Favorito> favoritos;

    @OneToMany(mappedBy = "fkCliente")
    private List<ClientePetshopSubscriber> inscritos;

    private LocalDate dataNasc;

    private String cpf;

    @OneToMany(mappedBy = "fkCliente", fetch = FetchType.LAZY)
    private List<Pet> pets;

    @OneToMany(mappedBy = "fkCliente", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;

//    private MailSender mailSender;
//    private SimpleMailMessage templateMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<AvaliacaoPetshop> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoPetshop> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public List<ClientePetshopSubscriber> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<ClientePetshopSubscriber> inscritos) {
        this.inscritos = inscritos;
    }

//    public void setMailSender(MailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void setTemplateMessage(SimpleMailMessage templateMessage) {
//        this.templateMessage = templateMessage;
//    }

//    @Override
//    public void atualiza(UsuarioClienteDto usuarioClienteDto) {
//        // Recipient's email ID needs to be mentioned.
//        // String to = "fromaddress@gmail.com";
//
//        // Sender's email ID needs to be mentioned
//        // String from = "toaddress@gmail.com";
//
//        // Assuming you are sending email from through gmails smtp
//         String host = "smtp.gmail.com";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//
//        // Get the Session object.// and pass username and password
//        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//
//            protected PasswordAuthentication getPasswordAuthentication() {
//
//                return new PasswordAuthentication(emailRemetente, "*******");
//
//            }
//
//        });
//
//        // Used to debug SMTP issues
//        session.setDebug(true);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(emailRemetente));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));
//
//            // Set Subject: header field
//            message.setSubject("This is the Subject Line!");
//
//            // Send the actual HTML message.
//            message.setContent(
//                    "<h1>Nova promoção!</h1>",
//                    "text/html");
//
//            System.out.println("sending...");
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
//        msg.setTo(usuarioClienteDto.getEmail());
//        msg.setText(
//                "Dear " + order.getCustomer().getFirstName()
//                        + order.getCustomer().getLastName()
//                        + ", thank you for placing order. Your order number is "
//                        + order.getOrderNumber());
//        try{
//            this.mailSender.send(msg);
//        }
//        catch (MailException ex) {
//            // simply log it and go on...
//            System.err.println(ex.getMessage());
//        }
//    }
}
