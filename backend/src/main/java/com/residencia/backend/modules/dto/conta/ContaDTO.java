package com.residencia.backend.modules.dto.conta;

import com.residencia.backend.modules.enums.TipoConta;
import lombok.Data;

@Data
public class ContaDTO {
  private String nome;
  private TipoConta tipoConta;
  private String banco;
}
