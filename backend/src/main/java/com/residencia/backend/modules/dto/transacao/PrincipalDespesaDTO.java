package com.residencia.backend.modules.dto.transacao;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PrincipalDespesaDTO {
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private String categoria;
    private String conta;
}
