package com.residencia.backend.modules.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponseDTO {
  private Integer id;
  private String nome;
  private String descricao;
}
