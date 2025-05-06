package com.residencia.backend.modules.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;


@Data
@Schema(description = "DTO de criação do usuário")
public class UsuarioDTO {
  @NotBlank(message = "O nome é obrigatório")
  @Schema(description = "Nome do usuário",example = "João Silva")
  private String nome;

  @CPF(message = "CPF inválido")
  @Schema(description = "CPF do usuário", example = "446.231.450-69")
  private String cpf;

  @Email(message = "Email inválido")
  @Schema(description = "Email do usuário",example = "joaosilva@gmail.com")
  private String email;

  @Pattern(regexp = "^\\d{2}\\s?\\d{4,5}\\s?\\d{4}$",
      message = "Telefone deve seguir o padrão: DD XXXXXXXX")
  @Schema(description = "Telefeone do usuário",example = "81 999999999")
  private String telefone;

  @Length(min = 6, max = 100, message = "Senha deve ter 6 a 100 caracteres")
  @Schema(description = "Senha do usuário",example = "123456")
  private String senha;
}
