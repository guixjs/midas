package com.residencia.backend.modules.models;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "usuario")
public class UsuarioEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Email(message = "O campo deve conter um email válido")
  private String email;

  @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$ \n")
  private String telefone;
  @Length(min = 6,max = 100, message = "A senha deve ter de 6 à 12 dígitos")
  private String senha;

  @NotBlank(message = "o nome não pode estar em branco")
   private String nome;


  @CPF(message = "O campo deve conter um CPF válido")
  private String cpf;

  @CreationTimestamp
  private LocalDateTime dt_criacao;

  public @CPF(message = "O campo deve conter um CPF válido") String getCpf() {
    return cpf;
  }

  public void setCpf(@CPF(message = "O campo deve conter um CPF válido") String cpf) {
    this.cpf = cpf;
  }

  public @Email(message = "O campo deve conter um email válido") String getEmail() {
    return email;
  }

  public void setEmail(@Email(message = "O campo deve conter um email válido") String email) {
    this.email = email;
  }


}

