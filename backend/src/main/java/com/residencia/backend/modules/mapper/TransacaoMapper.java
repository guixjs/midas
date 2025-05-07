package com.residencia.backend.modules.mapper;

import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseResumidoDTO;
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
    System.out.println("Valor recebido na transação: " + transacaoDTO.getValor());
    System.out.println("Data recebida: " + transacaoDTO.getDataTransacao());
    System.out.println("Categoria: " + categoria);
    System.out.println("ID Conta: " + idConta);
    System.out.println("Cartão: " + cartao);
    System.out.println("ID Usuário: " + idUsuario);

    BigDecimal valor = transacaoDTO.getValor();
    LocalDate data = transacaoDTO.getDataTransacao();

    // Verificando se o valor é null e logando essa condição
    if (valor == null) {
      System.out.println("Erro: o valor da transação é null!");
      throw new OperacaoNaoPermitidaException("O valor da transação não deve ser null");
    }

    if(transacaoDTO.getTipoTransacao() == null){
      throw new OperacaoNaoPermitidaException("O tipo da transação não deve ser null");
    }

    if(data == null){
      transacaoDTO.setDataTransacao(LocalDate.now());
    }

    if(transacaoDTO.getTipoTransacao() == TipoTransacao.DESPESA){
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

  public static TransacaoResponseResumidoDTO toResponseResumidoDTO(TransacaoEntity transacao){

    String nomeCartao = transacao.getCartao() != null ? transacao.getCartao().getNome() : "Transação não vinculada a um cartão";
    String nomeCategoria = transacao.getCategoria() != null ? transacao.getCategoria().getNome() : "Sem categoria";

    return TransacaoResponseResumidoDTO.builder()
        .id(transacao.getId())
        .valor(transacao.getValor())
        .descricao(transacao.getDescricao())
        .dataTransacao(transacao.getDataTransacao())
        .tipoTransacao(transacao.getTipoTransacao())
        .nomeConta(transacao.getConta().getNome())
        .nomeCategoria(nomeCategoria)
        .nomeCartao(nomeCartao)
        .build();
  }
}
