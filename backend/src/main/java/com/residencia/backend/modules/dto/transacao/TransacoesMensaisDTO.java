package com.residencia.backend.modules.dto.transacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacoesMensaisDTO {
    private Integer mes;
    private Integer ano;
    private Long totalTransacoes;
    private Long totalReceitas;
    private Long totalDespesas;
}