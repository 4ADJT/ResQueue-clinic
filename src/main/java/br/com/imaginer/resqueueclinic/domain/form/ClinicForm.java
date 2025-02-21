package br.com.imaginer.resqueueclinic.domain.form;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClinicForm {

    @NotBlank(message = "O nome da clínica é obrigatório")
    private String name;

    @NotBlank(message = "O endereço é obrigatório")
    private String address;

    @Column(nullable = false)
    private String phone;

    public ClinicForm(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public @NotBlank(message = "O nome da clínica é obrigatório") String getName() {
        return name;
    }

    public @NotBlank(message = "O endereço é obrigatório") String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

}
