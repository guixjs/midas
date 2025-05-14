package com.residencia.backend.modules.dto.recorrente;

import com.residencia.backend.modules.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecorrenteResponseResumidoDTO {
  private UUID id;
  private BigDecimal valor;
  private String descricao;
  private TipoTransacao tipoTransacao;
  private boolean repetirValor;
  private String nomeCategoria;
  private String nomeConta;
  private String nomeCartao;
}
