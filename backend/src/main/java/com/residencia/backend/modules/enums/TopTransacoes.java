package com.residencia.backend.modules.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum TopTransacoes {
  @Schema(description = "Mostra as transações criadas por último")
  RECENTES,
  @Schema(description = "Mostra as maiores receitas do mês")
  DESPESAS_MES,
  @Schema(description = "Mostra as maiores despesas do mês")
  RECEITAS_MES
}
