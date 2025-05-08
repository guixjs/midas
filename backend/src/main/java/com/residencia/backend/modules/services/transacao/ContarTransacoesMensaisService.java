package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.transacao.TransacoesMensaisDTO;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContarTransacoesMensaisService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public TransacoesMensaisDTO execute(UUID idUsuario, Integer mes, Integer ano) {
        Long totalReceitas = transacaoRepository.contarTransacoesPorTipoEMes(idUsuario, "RECEITA", mes, ano);
        Long totalDespesas = transacaoRepository.contarTransacoesPorTipoEMes(idUsuario, "DESPESA", mes, ano);
        Long totalTransacoes = totalReceitas + totalDespesas;

        return TransacoesMensaisDTO.builder()
                .mes(mes)
                .ano(ano)
                .totalTransacoes(totalTransacoes)
                .totalReceitas(totalReceitas)
                .totalDespesas(totalDespesas)
                .build();
    }
}