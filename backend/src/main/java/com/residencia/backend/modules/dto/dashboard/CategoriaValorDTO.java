package com.residencia.backend.modules.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CategoriaValorDTO {
  private String nomeCategoria;
  private BigDecimal valor;
}
