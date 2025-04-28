package com.residencia.backend.modules.dto.transacao;

import com.residencia.backend.modules.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResponseResumidoDTO {
  private UUID id;
  private BigDecimal valor;
  private String descricao;
  private LocalDate dataTransacao;
  private TipoTransacao tipoTransacao;
  private String nomeCategoria;
  private String nomeConta;

  private String nomeCartao;
}
