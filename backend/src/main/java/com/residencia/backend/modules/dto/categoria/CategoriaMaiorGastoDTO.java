package com.residencia.backend.modules.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaMaiorGastoDTO {
    private String nome;
    private BigDecimal valorTotal;
    private Long quantidade;
    private Double percentual;
}