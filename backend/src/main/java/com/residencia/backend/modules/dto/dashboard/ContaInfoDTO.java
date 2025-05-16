package com.residencia.backend.modules.dto.dashboard;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaInfoDTO {
  private Integer idConta;
  private String nomeConta;
  private BigDecimal saldo;
  private BigDecimal totalReceitas;
  private BigDecimal totalDespesas;
}
