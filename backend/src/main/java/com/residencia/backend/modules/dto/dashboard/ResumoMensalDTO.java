package com.residencia.backend.modules.dto.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
public class ResumoMensalDTO {
  private YearMonth mes;
  private Integer quantidadeTransacoes;
  private BigDecimal saldoDoMes;
  private BigDecimal receitasDoMes;
  private BigDecimal despesasDoMes;

  public ResumoMensalDTO(int ano, int mes, Long quantidadeTransacoes, BigDecimal saldoDoMes, BigDecimal receitasDoMes, BigDecimal despesasDoMes) {
    this.mes = YearMonth.of(ano, mes);
    this.quantidadeTransacoes = quantidadeTransacoes.intValue();
    this.saldoDoMes = saldoDoMes;
    this.receitasDoMes = receitasDoMes;
    this.despesasDoMes = despesasDoMes;
  }
}
