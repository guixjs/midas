package com.residencia.backend.modules.dto.transacao;


import com.residencia.backend.modules.enums.TipoTransacao;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class TransacaoDTO {
  private String descricao;
  private LocalDate dataTransacao;
  private BigDecimal valor;
  private TipoTransacao tipoTransacao;
  private Integer idCategoria;
  private Integer idConta;
  private UUID idCartao;
}

