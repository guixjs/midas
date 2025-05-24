package com.residencia.backend.modules.dto.conta;


import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponseDTO {
  private Integer idConta;
  private String nome;
  private TipoConta tipoConta;
  private String banco;
  private String cor;
  private BigDecimal saldo;
  private UsuarioResponseResumidoDTO usuario;
}
