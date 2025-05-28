package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.CartaoEntity;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.repositories.CartaoRepository;
import com.residencia.backend.modules.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExcluirContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public void execute(Integer id, UUID idUsuario) {
        ContaEntity conta = contaRepository.findByIdAndIdUsuario(id, idUsuario)
            .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta não encontrada ou sem permissão"));

        if (conta.getNome().equalsIgnoreCase("Padrão")) {
            throw new OperacaoNaoPermitidaException("A conta Padrão não pode ser excluída");
        }

        Optional<List<CartaoEntity>> cartoes = cartaoRepository.findByIdUsuarioAndIdConta(idUsuario,id);
        cartoes.ifPresent(cartaoEntities -> cartaoRepository.deleteAll(cartaoEntities));

        contaRepository.delete(conta);
    }
}
