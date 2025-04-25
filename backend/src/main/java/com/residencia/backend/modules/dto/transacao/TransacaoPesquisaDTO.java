package com.residencia.backend.modules.dto.transacao;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TransacaoPesquisaDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer idCategoria;
}