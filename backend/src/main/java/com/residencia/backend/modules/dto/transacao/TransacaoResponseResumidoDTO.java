package com.residencia.backend.modules.dto.transacao;

import com.residencia.backend.modules.enums.TipoTransacao;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO de resposta de transação")
public class TransacaoResponseResumidoDTO {
  @Schema(description = "Id da transação")
  private UUID id;
  @Schema(description = "Valor da transação")
  private BigDecimal valor;
  @Schema(description = "Descrição da transação")
  private String descricao;
  @Schema(description = "Data da transação")
  private LocalDate dataTransacao;
  @Schema(description = "Tipo da transação (receita ou despesas)")
  private TipoTransacao tipoTransacao;
  @Schema(description = "Nome da categoria à qual a transação pode ser relacionada, caso possua")
  private String nomeCategoria;
  @Schema(description = "Nome da conta à qual a transação pertence")
  private String nomeConta;
  @Schema(description = "Nome do cartão à qual a transação pode pertencer, caso pertença")
  private String nomeCartao;
}
