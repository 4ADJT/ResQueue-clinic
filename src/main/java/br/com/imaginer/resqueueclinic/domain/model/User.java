package br.com.imaginer.resqueueclinic.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(schema = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @Email
        private String email;

}
