package com.residencia.backend.modules.dto.transacao;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransacaoPesquisaDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer idCategoria;
    private String tipoTransacao;
    private boolean possuiCartao;
    private Integer idConta;
    private BigDecimal valorMin;
    private BigDecimal valorMax;

}