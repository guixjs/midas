package com.residencia.backend.modules.dto.conta;

import com.residencia.backend.modules.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponseResumidoDTO {
  private String nome;
  private TipoConta tipoConta;
  private String banco;
}
