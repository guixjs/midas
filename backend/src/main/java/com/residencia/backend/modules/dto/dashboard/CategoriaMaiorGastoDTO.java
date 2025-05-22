package com.residencia.backend.modules.dto.dashboard;

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
    private String nomeCategoria;
    private BigDecimal valorGasto;
    private BigDecimal percentual;
    private String corCategoria;
}