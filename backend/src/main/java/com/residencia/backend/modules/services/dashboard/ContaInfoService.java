package com.residencia.backend.modules.services.dashboard;

import com.residencia.backend.modules.dto.dashboard.ContaInfoDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.repositories.ContaRepository;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContaInfoService {

  @Autowired
  private TransacaoRepository transacaoRepository;
  @Autowired
  private ContaRepository contaRepository;

  public ContaInfoDTO montarContaInfo(UUID idUsuario, Integer idConta){

    List<ContaEntity> contas;
    String nomeConta;

    if(idConta!=null){
      ContaEntity conta = contaRepository.findByIdAndIdUsuario(idConta, idUsuario)
          .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta não encontrada"));
      contas = List.of(conta);
      nomeConta = conta.getNome();
    }else{
      contas = contaRepository.findAllByIdUsuario(idUsuario);
      nomeConta = "Todas as contas";
    }

    BigDecimal saldoTotal = BigDecimal.ZERO;
    BigDecimal totalReceitas = BigDecimal.ZERO;
    BigDecimal totalDespesas = BigDecimal.ZERO;

    for (ContaEntity conta : contas) {
      BigDecimal receitas = Optional.ofNullable(transacaoRepository.somaReceitasPorConta(conta.getId())).orElse(BigDecimal.ZERO);
      BigDecimal despesas = Optional.ofNullable(transacaoRepository.somaDespesasPorConta(conta.getId())).orElse(BigDecimal.ZERO);

      saldoTotal = saldoTotal.add(receitas).subtract(despesas.abs());
      totalReceitas = totalReceitas.add(receitas);
      totalDespesas = totalDespesas.add(despesas.abs());
    }

    return ContaInfoDTO.builder()
        .idConta(idConta)
        .nomeConta(nomeConta)
        .saldo(saldoTotal)
        .totalReceitas(totalReceitas)
        .totalDespesas(totalDespesas)
        .build();
  }
}
