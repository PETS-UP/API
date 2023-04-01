package sptech.school.loginlogoff.objetos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class DonoPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotBlank
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
