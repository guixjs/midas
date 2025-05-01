package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExcluirTransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;
  public void execute(UUID id, UUID idUsuario) {
    TransacaoEntity transacao = transacaoRepository.findByIdAndIdUsuario(id, idUsuario)
        .orElseThrow(() -> new OperacaoNaoPermitidaException("Transação não encontrada"));
    transacaoRepository.delete(transacao);
  }
}
