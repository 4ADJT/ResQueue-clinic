package br.com.imaginer.resqueueclinic.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
@Table(schema = "user_clinic")
public record User (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        UUID id,

        @Email
        @NotBlank(message = "O e-mail é obrigatório")
        String email

) {}
