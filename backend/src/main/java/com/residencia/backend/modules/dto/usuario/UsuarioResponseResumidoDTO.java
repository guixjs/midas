package com.residencia.backend.modules.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseResumidoDTO {
  private String nome;
  private String email;
  private String telefone;
}
