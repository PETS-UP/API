package sptech.school.loginlogoff.objetos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private DonoPet donoPet;

    @NotBlank
    private String tipo;

    @NotBlank
    private String raca;

    public DonoPet getDonoPet() {
        return donoPet;
    }

    public void setDonoPet(DonoPet donoPet) {
        this.donoPet = donoPet;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
