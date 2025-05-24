package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.enums.OperacoesRealizadas;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.validator.TransacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AtualizarSaldoService {

  @Autowired
  private TransacaoValidator transacaoValidator;


  public void atualizarSaldo(TransacaoEntity transacao, OperacoesRealizadas operacao) {
    ContaEntity conta =  transacaoValidator.getContaById(transacao.getIdConta());
    BigDecimal valor = transacao.getValor();

    if(operacao == OperacoesRealizadas.EXCLUSAO){
      conta.setSaldo(conta.getSaldo().subtract(valor));
    }
    if(operacao == OperacoesRealizadas.CRIACAO){
      conta.setSaldo(conta.getSaldo().add(valor));
    }

  }
}
