package com.residencia.backend.modules.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;


@Data
public class UsuarioDTO {
  @NotBlank(message = "O nome é obrigatório")
  private String nome;

  @CPF(message = "CPF inválido")
  private String cpf;

  @Email(message = "Email inválido")
  private String email;

  @Pattern(regexp = "^\\d{2}\\s?\\d{4,5}\\s?\\d{4}$",
      message = "Telefone deve seguir o padrão: DD XXXXXXXX")
  private String telefone;

  @Length(min = 6, max = 100, message = "Senha deve ter 6 a 100 caracteres")
  private String senha;
}
