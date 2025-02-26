package br.com.imaginer.resqueueclinic.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Table(name = "clinics")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotBlank(message = "O nome da clínica é obrigatório")
    private String name;

    @NotBlank(message = "O endereço é obrigatório")
    private String address;

    @Column(nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Clinic(String name, String address, String phone, User user) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.user = user;
    }

}
