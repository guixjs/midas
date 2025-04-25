package com.residencia.backend.modules.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
  private UUID id;
  private String nome;
  private String cpf;
  private String email;
  private String telefone;
  private String senha;
}
