package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.transacao.PrincipalDespesaDTO;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarPrincipaisDespesasService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<PrincipalDespesaDTO> execute(UUID idUsuario) {
        List<PrincipalDespesaDTO> despesas = transacaoRepository.findTop10DespesasByValor(idUsuario);
        return despesas.size() > 10 ? despesas.subList(0, 10) : despesas;
    }
}