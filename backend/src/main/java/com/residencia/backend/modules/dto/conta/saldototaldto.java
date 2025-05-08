package com.residencia.backend.modules.dto.conta;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SaldoTotalDTO {
    private BigDecimal totalReceitas;
    private BigDecimal totalDespesas;
    private BigDecimal saldoTotal;
    private boolean saldoPositivo;
}
