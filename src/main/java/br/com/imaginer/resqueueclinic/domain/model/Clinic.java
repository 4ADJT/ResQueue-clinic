package br.com.imaginer.resqueueclinic.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "clinics")

public record Clinic (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        Long id,

        @NotBlank(message = "O nome da clínica é obrigatório")
        String name,

        @NotBlank(message = "O endereço é obrigatório")
        String addres,

        @Column(nullable = false)
        String phone
) {

    public Clinic(String name, String address, String phone) {
        this(null, name, address, phone);
    }

}
