package br.com.imaginer.resqueueclinic.domain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
@Table(name = "user_clinic") //(schema = "user_clinic")
public class User {
    @Id
    private UUID id;

    @Email
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    public User(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @Email @NotBlank(message = "O e-mail é obrigatório") String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank(message = "O e-mail é obrigatório") String email) {
        this.email = email;
    }
}
