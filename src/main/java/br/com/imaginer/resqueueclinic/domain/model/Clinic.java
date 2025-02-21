package br.com.imaginer.resqueueclinic.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clinics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O nome da clínica é obrigatório")
    private String name;

    @NotBlank(message = "O endereço é obrigatório")
    private String address;

    @Column(nullable = false)
    private String phone;


    public Clinic(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

}
