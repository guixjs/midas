package com.residencia.backend.modules.services.dashboard;

import com.residencia.backend.modules.dto.transacao.TransacaoResponseResumidoDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.enums.TopTransacoes;
import com.residencia.backend.modules.mapper.TransacaoMapper;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
public class TopTransacoesService {

  @Autowired
  private TransacaoRepository transacaoRepository;

  public List<TransacaoResponseResumidoDTO> montarListaTransacoesDashboard(UUID idUsuario, TopTransacoes top, YearMonth mesAtual){
    Pageable qtdTransacoes = PageRequest.of(0,5);
    List<TransacaoEntity> transacoes;
    int mes = mesAtual.getMonthValue();
    int ano = mesAtual.getYear();


    if(top == null){
      top = TopTransacoes.RECENTES;
    }

    switch (top){
      case DESPESAS_MES:
        transacoes = transacaoRepository.buscarTopTransacoesPorTipoEMes(idUsuario, TipoTransacao.DESPESA,ano,mes, qtdTransacoes);
        break;
      case RECEITAS_MES:
        transacoes = transacaoRepository.buscarTopTransacoesPorTipoEMes(idUsuario, TipoTransacao.RECEITA,ano,mes, qtdTransacoes);
        break;
      case RECENTES:
      default:
        transacoes = transacaoRepository.findByUsuarioIdOrderByDataCriacaoDesc(idUsuario, qtdTransacoes);
        break;
    }

    return transacoes.stream()
        .map(TransacaoMapper::toResponseResumidoDTO)
        .toList();


  }

}
