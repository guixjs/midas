package com.residencia.backend.modules.dto.categoria;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CategoriaMaiorGastoDTO {
    private String nomeCategoria;
    private BigDecimal valorTotal;
    private Long quantidadeTransacoes;
    private Double percentualDoTotal;
}
