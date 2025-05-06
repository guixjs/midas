package com.residencia.backend.modules.dto.transacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "DTO de filtro das transações")
public class TransacaoPesquisaDTO {
    @Schema(description = "Data inicial para filtrar transações (inclusive)")
    private LocalDate dataInicio;
    @Schema(description = "Data final para filtrar transações (inclusive)")
    private LocalDate dataFim;
    @Schema(description = "ID da categoria a ser filtrada")
    private Integer idCategoria;
    @Schema(description = "Tipo da transação: RECEITA ou DESPESA")
    private String tipoTransacao;
    @Schema(description = "Indica se a transação está vinculada a um cartão")
    private boolean possuiCartao;
    @Schema(description = "ID da conta associada à transação")
    private Integer idConta;
    @Schema(description = "Valor mínimo da transação")
    private BigDecimal valorMin;
    @Schema(description = "Valor máximo da transação")
    private BigDecimal valorMax;
}