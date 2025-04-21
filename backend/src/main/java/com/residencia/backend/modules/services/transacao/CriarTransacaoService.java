package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.categoria.CategoriaResponseDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.ContaRepository;
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

  @Autowired
  private ContaRepository contaRepository;

  private TransacaoEntity execute(TransacaoEntity transacao){
    return this.transacaoRepository.save(transacao);
  }

  public TransacaoResponseDTO criarTransacao(TransacaoDTO transacaoDTO, UUID idUsuario){
    CategoriaEntity categoriaEntity = null;

    CategoriaResponseDTO categoriaResponse = null;

    BigDecimal valor = transacaoDTO.getValor();

    Integer idConta = transacaoDTO.getIdConta();

    if(transacaoDTO.getTipoTransacao() == TipoTransacao.DEBITO){
      valor = valor.negate();
    }
    if(transacaoDTO.getId_categoria()!= null){
      categoriaEntity = this.categoriaRepository.findById(transacaoDTO.getId_categoria())
          .orElseThrow(()->{
            throw new RuntimeException("Categoria não encontrada");
          });
    }

    if(idConta==null){
      transacaoDTO.setIdConta(1);
    }
    if (idConta == null) {
      var contaGeral = contaRepository.findByIdUsuarioAndNome(idUsuario,"Geral")
          .orElseThrow(() -> new RuntimeException("Conta Geral não encontrada"));
      idConta = contaGeral.getId();
    }
    var transacao = TransacaoEntity.builder()
        .descricao(transacaoDTO.getDescricao())
        .dataTransacao(transacaoDTO.getData_transacao())
        .valor(valor)
        .tipoTransacao(transacaoDTO.getTipoTransacao())
        .categoria(categoriaEntity)
        .idUsuario(idUsuario)
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
        .data_transacao(resultado.getDataTransacao())
        .valor(resultado.getValor())
        .tipoTransacao(resultado.getTipoTransacao())
        .categoria(categoriaResponse)
        .idUsuario(resultado.getIdUsuario())
        .build();

    return response;
  }

}
