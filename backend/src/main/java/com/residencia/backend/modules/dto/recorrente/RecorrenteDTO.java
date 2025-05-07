package com.residencia.backend.modules.dto.recorrente;

import com.residencia.backend.modules.enums.TipoTransacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "DTO de criação de transações recorrentes")
public class RecorrenteDTO {
  @Schema(description = "Descrição da transação recorrente")
  private String descricao;
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
  @Schema(description = "Boolean que irá informar se o valor da transação sempre será o mesmo ou se mudará")
  private boolean repetirValor;
}
