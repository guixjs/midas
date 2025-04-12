package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.categoria.CategoriaResponseDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CriarTransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;

  @Autowired
  private CategoriaRepository categoriaRepository;

  private TransacaoEntity execute(TransacaoEntity transacao){
    return this.transacaoRepository.save(transacao);
  }

  public TransacaoResponseDTO criarTransacao(TransacaoDTO transacaoDTO, UUID idUsuario){
    CategoriaEntity categoriaEntity = null;

    CategoriaResponseDTO categoriaResponse = null;

    BigDecimal valor = transacaoDTO.getValor();

    if(transacaoDTO.getTipoTransacao() == TipoTransacao.DEBITO){
      valor = valor.negate();
    }
    if(transacaoDTO.getId_categoria()!= null){
      categoriaEntity = this.categoriaRepository.findById(transacaoDTO.getId_categoria())
          .orElseThrow(()->{
            throw new RuntimeException("Categoria não encontrada");
          });
    }

    var transacao = TransacaoEntity.builder()
        .descricao(transacaoDTO.getDescricao())
        .data_transacao(transacaoDTO.getData_transacao())
        .valor(valor)
        .tipoTransacao(transacaoDTO.getTipoTransacao())
        .categoria(categoriaEntity)
        .id_usuario(idUsuario)
        .build();


    var resultado = execute(transacao);

    if(resultado.getCategoria()!=null){
      categoriaResponse = CategoriaResponseDTO.builder()
          .id(resultado.getCategoria().getId())
          .nome(resultado.getCategoria().getNome())
          .descricao(resultado.getCategoria().getDescricao())
          .build();
    }

    TransacaoResponseDTO response = TransacaoResponseDTO.builder()
        .id(resultado.getId())
        .descricao(resultado.getDescricao())
        .data_transacao(resultado.getData_transacao())
        .valor(resultado.getValor())
        .tipoTransacao(resultado.getTipoTransacao())
        .categoria(categoriaResponse)
        .id_usuario(resultado.getId_usuario())
        .build();

    return response;
  }

}
