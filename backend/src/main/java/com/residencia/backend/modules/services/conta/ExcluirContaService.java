package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExcluirContaService {

    @Autowired
    private ContaRepository contaRepository;

    public void execute(Integer id, UUID idUsuario) {
        ContaEntity conta = contaRepository.findByIdAndIdUsuario(id, idUsuario)
            .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta não encontrada ou sem permissão"));

        if (conta.getNome().equalsIgnoreCase("Geral")) {
            throw new OperacaoNaoPermitidaException("A conta Geral não pode ser excluída");
        }

        contaRepository.delete(conta);
    }
}
