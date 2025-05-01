package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseResumidoDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.mapper.TransacaoMapper;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EditarTransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;
  @Autowired
  private CategoriaRepository categoriaRepository;

  public TransacaoResponseResumidoDTO execute(UUID id, TransacaoDTO transacaoDTO, UUID idUsuario) {
    TransacaoEntity transacao = transacaoRepository.findByIdAndIdUsuario(id, idUsuario)
        .orElseThrow(() -> new OperacaoNaoPermitidaException("Transação não encontrada"));

    if(transacaoDTO.getValor() != null){
      transacao.setValor(transacaoDTO.getValor());
    }
    if(transacaoDTO.getDescricao() != null){
      transacao.setDescricao(transacaoDTO.getDescricao());
    }
    if(transacaoDTO.getDataTransacao() != null){
      transacao.setDataTransacao(transacaoDTO.getDataTransacao());
    }
    if(transacaoDTO.getIdCategoria() != null){
      var categoria = categoriaRepository.findById(transacaoDTO.getIdCategoria())
          .orElseThrow(() -> new OperacaoNaoPermitidaException("Categoria não encontrada"));
      transacao.setIdCategoria(categoria.getId());
    }
    if(transacaoDTO.getTipoTransacao() != null){
      transacao.setTipoTransacao(transacaoDTO.getTipoTransacao());
    }

    var resultado = transacaoRepository.save(transacao);
    return TransacaoMapper.toResponseResumidoDTO(resultado);
  }
}
