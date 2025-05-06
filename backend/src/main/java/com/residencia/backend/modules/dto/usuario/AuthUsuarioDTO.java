package com.residencia.backend.modules.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de autenticação de usuário")
public class AuthUsuarioDTO {
  @Schema(description = "CPF do usuário já cadastrado, usado para login", example = "446.231.450-69")
  private String cpf;
  @Schema(description = "Senha do usuário já cadastrado",example = "123456")
  private String senha;
}
