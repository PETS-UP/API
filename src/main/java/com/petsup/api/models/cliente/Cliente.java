package com.petsup.api.models.cliente;

import com.petsup.api.models.Agendamento;
import com.petsup.api.models.AvaliacaoPetshop;
import com.petsup.api.models.Favorito;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Cliente")
public class Cliente /*implements ClienteObserver*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    private String senha;

    private String telefone;

    private String cep;

    private String estado;

    private String cidade;

    private String bairro;

    private String rua;

    private String numero;

    @OneToMany(mappedBy = "fkCliente")
    private List<AvaliacaoPetshop> avaliacoes;

    @OneToMany(mappedBy = "fkCliente")
    private List<Favorito> favoritos;

    @OneToMany(mappedBy = "fkCliente")
    private List<ClienteSubscriber> inscritos;

    private LocalDate dataNasc;

    private String cpf;

    private double latitude;

    private double longitude;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public List<ClienteSubscriber> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<ClienteSubscriber> inscritos) {
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
