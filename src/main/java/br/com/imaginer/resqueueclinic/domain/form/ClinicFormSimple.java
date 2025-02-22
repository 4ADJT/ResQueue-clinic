package br.com.imaginer.resqueueclinic.domain.form;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class ClinicFormSimple {

    @NotBlank(message = "O nome da clínica é obrigatório")
    private String name;

    @NotBlank(message = "O endereço é obrigatório")
    private String address;

    @Column(nullable = false)
    private String phone;

    public ClinicFormSimple(String name, String address, String phone) {
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
