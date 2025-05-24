package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseResumidoDTO;
import com.residencia.backend.modules.enums.OperacoesRealizadas;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.mapper.TransacaoMapper;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import com.residencia.backend.modules.services.conta.AtualizarSaldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EditarTransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;
  @Autowired
  private CategoriaRepository categoriaRepository;
  @Autowired
  private AtualizarSaldoService atualizarSaldoService;

  @Transactional
  public TransacaoResponseResumidoDTO execute(UUID id, TransacaoDTO transacaoDTO, UUID idUsuario) {
    TransacaoEntity transacaoAntiga = transacaoRepository.findByIdAndIdUsuario(id, idUsuario)
        .orElseThrow(() -> new OperacaoNaoPermitidaException("Transação não encontrada"));
    atualizarSaldoService.atualizarSaldo(transacaoAntiga, OperacoesRealizadas.EXCLUSAO);

    if(transacaoDTO.getValor() != null){
      transacaoAntiga.setValor(transacaoDTO.getValor());
    }
    if(transacaoDTO.getDescricao() != null){
      transacaoAntiga.setDescricao(transacaoDTO.getDescricao());
    }
    if(transacaoDTO.getDataTransacao() != null){
      transacaoAntiga.setDataTransacao(transacaoDTO.getDataTransacao());
    }
    if(transacaoDTO.getIdCategoria() != null){
      var categoria = categoriaRepository.findById(transacaoDTO.getIdCategoria())
          .orElseThrow(() -> new OperacaoNaoPermitidaException("Categoria não encontrada"));
      transacaoAntiga.setIdCategoria(categoria.getId());
    }
    if(transacaoDTO.getTipoTransacao() != null){
      transacaoAntiga.setTipoTransacao(transacaoDTO.getTipoTransacao());
    }

    var transacaoAtualizada = transacaoRepository.save(transacaoAntiga);

    atualizarSaldoService.atualizarSaldo(transacaoAtualizada, OperacoesRealizadas.CRIACAO);

    return TransacaoMapper.toResponseResumidoDTO(transacaoAtualizada);
  }
}
