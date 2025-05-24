package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.enums.OperacoesRealizadas;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import com.residencia.backend.modules.services.conta.AtualizarSaldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ExcluirTransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;

  @Autowired
  private AtualizarSaldoService atualizarSaldoService;

  @Transactional
  public void execute(UUID id, UUID idUsuario) {
    TransacaoEntity transacao = transacaoRepository.findByIdAndIdUsuario(id, idUsuario)
        .orElseThrow(() -> new OperacaoNaoPermitidaException("Transação não encontrada"));
    atualizarSaldoService.atualizarSaldo(transacao, OperacoesRealizadas.EXCLUSAO);
    transacaoRepository.delete(transacao);
  }
}
