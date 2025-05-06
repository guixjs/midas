package com.residencia.backend.modules.dto.transacao;


import com.residencia.backend.modules.enums.TipoTransacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "DTO de criação de transações")
public class TransacaoDTO {
  @Schema(description = "Descrição de transação")
  private String descricao;
  @Schema(description = "Data da transação")
  private LocalDate dataTransacao;
  @Schema(description = "Valor da transação")
  private BigDecimal valor;
  @Schema(description = "Tipo da transação (receita ou despesas)")
  private TipoTransacao tipoTransacao;
  @Schema(description = "Id da categoria à qual a transação é relacionada")
  private Integer idCategoria;
  @Schema(description = "Id da conta à qual a transação pertence")
  private Integer idConta;
  @Schema(description = "Id do cartão à qual a transação pertence",nullable = true)
  private UUID idCartao;
}

