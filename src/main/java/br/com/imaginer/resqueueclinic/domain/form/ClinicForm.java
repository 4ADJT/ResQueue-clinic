package br.com.imaginer.resqueueclinic.domain.form;


import br.com.imaginer.resqueueclinic.domain.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClinicForm {

    @NotBlank(message = "O nome da clínica é obrigatório")
    private String name;

    @NotBlank(message = "O endereço é obrigatório")
    private String address;

    @Column(nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
