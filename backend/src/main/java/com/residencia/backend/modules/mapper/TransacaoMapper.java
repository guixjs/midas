package com.residencia.backend.modules.mapper;

import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.CartaoEntity;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.TransacaoEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class TransacaoMapper {

  public static TransacaoEntity toEntity(TransacaoDTO transacaoDTO, CategoriaEntity categoria, Integer idConta, CartaoEntity cartao, UUID idUsuario){
    BigDecimal valor = transacaoDTO.getValor();
    LocalDate data = transacaoDTO.getDataTransacao();

    if(valor == null){
      throw new OperacaoNaoPermitidaException("O valor da transação não deve ser null");
    }
    if(transacaoDTO.getTipoTransacao() == null){
      throw new OperacaoNaoPermitidaException("O tipo da transação não deve ser null");
    }

    if(data == null){
      transacaoDTO.setDataTransacao(LocalDate.now());
    }

    if(transacaoDTO.getTipoTransacao() == TipoTransacao.DEBITO){
      valor = valor.negate();
    }

    return TransacaoEntity.builder()
        .descricao(transacaoDTO.getDescricao())
        .dataTransacao(transacaoDTO.getDataTransacao())
        .valor(valor)
        .tipoTransacao(transacaoDTO.getTipoTransacao())
        .idUsuario(idUsuario)
        .idConta(idConta)
        .idCategoria(Optional.ofNullable(categoria).map(CategoriaEntity::getId).orElse(null))
        .idCartao(Optional.ofNullable(cartao).map(CartaoEntity::getId).orElse(null))
        .build();
  }

  public static TransacaoResponseDTO toResponseDTO(
      TransacaoEntity resultado,
      CategoriaResponseResumidoDTO categoriaResponse,
      CartaoResponseResumidoDTO cartaoResponse,
      UsuarioResponseResumidoDTO usuarioResponse,
      ContaResponseResumidoDTO contaResponse){

    return TransacaoResponseDTO.builder()
        .id(resultado.getId())
        .descricao(resultado.getDescricao())
        .dataTransacao(resultado.getDataTransacao())
        .valor(resultado.getValor())
        .tipoTransacao(resultado.getTipoTransacao())
        .conta(contaResponse)
        .cartao(cartaoResponse)
        .usuario(usuarioResponse)
        .categoria(categoriaResponse)
        .build();
  }
}
