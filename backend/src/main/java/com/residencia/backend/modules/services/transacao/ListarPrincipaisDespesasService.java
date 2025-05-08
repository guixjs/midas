package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.transacao.PrincipalDespesaDTO;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarPrincipaisDespesasService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<PrincipalDespesaDTO> execute(UUID idUsuario) {
        Pageable pageable = PageRequest.of(0, 10); // Pegando as 10 maiores despesas
        return transacaoRepository.findTop10DespesasByValor(idUsuario, pageable);
    }
}
