package com.residencia.backend.modules.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUsuarioDTO {
  private String cpf;
  private String senha;
}
