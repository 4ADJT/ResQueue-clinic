package br.com.imaginer.resqueueclinic.domain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "user_clinic")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  private UUID id;

  @Email
  @NotBlank(message = "O e-mail é obrigatório")
  private String email;

  public @Email @NotBlank(message = "O e-mail é obrigatório") String getEmail() {
    return email;
  }

  public void setEmail(@Email @NotBlank(message = "O e-mail é obrigatório") String email) {
    this.email = email;
  }
}
