package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.categoria.CategoriaResponseDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.CartaoRepository;
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

  @Autowired
  private CartaoRepository cartaoRepository;

  private TransacaoEntity execute(TransacaoEntity transacao){
    return this.transacaoRepository.save(transacao);
  }

  public TransacaoResponseDTO criarTransacao(TransacaoDTO transacaoDTO, UUID idUsuario){
    CategoriaEntity categoriaEntity = null;

    CategoriaResponseDTO categoriaResponse = null;

    BigDecimal valor = transacaoDTO.getValor();

    Integer idConta = transacaoDTO.getIdConta();
    System.out.println(idConta);

    if(transacaoDTO.getTipoTransacao() == TipoTransacao.DEBITO){
      valor = valor.negate();
    }


    if(transacaoDTO.getIdCategoria()!= null){
      categoriaEntity = this.categoriaRepository.findById(transacaoDTO.getIdCategoria())
          .orElseThrow(()-> new RuntimeException("Categoria não encontrada"));
    }

    if (idConta == null) {
      var contaGeral = contaRepository.findByIdUsuarioAndNome(idUsuario,"Geral")
          .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta Geral não encontrada"));
      idConta = contaGeral.getId();
    } else {
      var contaEspecifica = contaRepository.findByIdAndIdUsuario(idConta, idUsuario)
          .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta informada não encontrada para este usuário"));
      idConta = contaEspecifica.getId();
    }
    if(transacaoDTO.getIdCartao() != null){
      var cartao = cartaoRepository.findByIdAndIdContaAndIdUsuario(transacaoDTO.getIdCartao(),idConta,idUsuario)
          .orElseThrow(()-> new OperacaoNaoPermitidaException("Cartão não encontrado"));
      if(!cartao.getIdConta().equals(idConta)){
        throw new OperacaoNaoPermitidaException("O cartão informado não encontrado para essa conta");
      }
    }

    var transacao = TransacaoEntity.builder()
        .descricao(transacaoDTO.getDescricao())
        .dataTransacao(transacaoDTO.getDataTransacao())
        .valor(valor)
        .tipoTransacao(transacaoDTO.getTipoTransacao())
        .categoria(categoriaEntity)
        .idUsuario(idUsuario)
        .idConta(idConta)
        .idCartao(transacaoDTO.getIdCartao())
        .build();

    if (transacaoDTO.getIdCartao() != null) {
      var cartao = cartaoRepository.findById(transacaoDTO.getIdCartao())
          .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

      transacao.setCartaoEntity(cartao); // se usar builder e quiser setar depois
    }

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
        .dataTransacao(resultado.getDataTransacao())
        .valor(resultado.getValor())
        .tipoTransacao(resultado.getTipoTransacao())
        .idConta(resultado.getIdConta())
        .idCartao(resultado.getIdCartao())
        .categoria(categoriaResponse)
        .idUsuario(resultado.getIdUsuario())
        .build();

    return response;
  }

}
