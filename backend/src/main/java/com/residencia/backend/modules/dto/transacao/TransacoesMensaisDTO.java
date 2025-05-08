package com.residencia.backend.modules.dto.transacao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransacoesMensaisDTO {
    private Integer mes;
    private Integer ano;
    private Long totalTransacoes;
    private Long totalReceitas;
    private Long totalDespesas;
}