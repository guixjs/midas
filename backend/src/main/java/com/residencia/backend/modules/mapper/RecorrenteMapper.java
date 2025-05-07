package com.residencia.backend.modules.mapper;

import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.recorrente.RecorrenteDTO;

import com.residencia.backend.modules.dto.recorrente.RecorrenteResponseDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.CartaoEntity;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.RecorrenteEntity;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecorrenteMapper {

  public static RecorrenteEntity toEntity(RecorrenteDTO recorrenteDTO, CategoriaEntity categoria, Integer idConta, CartaoEntity cartao, UUID idUsuario){
    BigDecimal valor = null;
    if (recorrenteDTO.isRepetirValor() && recorrenteDTO.getValor() == null) {
      throw new OperacaoNaoPermitidaException("É preciso informar um valor se 'repetirValor' estiver marcado.");
    }

    if(recorrenteDTO.getValor() != null && recorrenteDTO.getTipoTransacao().equals(TipoTransacao.DESPESA)){
       valor = recorrenteDTO.getValor().negate();
      }

    return RecorrenteEntity.builder()
        .descricao(recorrenteDTO.getDescricao())
        .valor(valor)
        .repetirValor(recorrenteDTO.isRepetirValor())
        .tipoTransacao(recorrenteDTO.getTipoTransacao())
        .idUsuario(idUsuario)
        .idConta(idConta)
        .idCategoria(Optional.ofNullable(categoria).map(CategoriaEntity::getId).orElse(null))
        .idCartao(Optional.ofNullable(cartao).map(CartaoEntity::getId).orElse(null))
        .build();
  }

  public static RecorrenteResponseDTO toResponseDTO(
      RecorrenteEntity resultado,
      CategoriaResponseResumidoDTO categoriaResponse,
      CartaoResponseResumidoDTO cartaoResponse,
      UsuarioResponseResumidoDTO usuarioResponse,
      ContaResponseResumidoDTO contaResponse){

    return RecorrenteResponseDTO.builder()
        .id(resultado.getId())
        .valor(resultado.getValor())
        .descricao(resultado.getDescricao())
        .tipoTransacao(resultado.getTipoTransacao())
        .repetirValor(resultado.isRepetirValor())
        .categoria(categoriaResponse)
        .conta(contaResponse)
        .cartao(cartaoResponse)
        .usuario(usuarioResponse)
        .build();
  }


  public static TransacaoDTO toTransacaoDTO(RecorrenteEntity recorrente){

    BigDecimal valorVerificado = null;

    if(recorrente.isRepetirValor()){
      valorVerificado = recorrente.getValor();
    }

//    return TransacaoDTO.builder()
//        .descricao(recorrente.getDescricao())
//        .valor(recorrente.getValor())
//        .tipoTransacao(recorrente.getTipoTransacao())
//        .valor(valorVerificado)
//        .dataTransacao(LocalDate.now())
//        .idConta(recorrente.getIdConta())
//        .idCartao(recorrente.getIdCartao())
//        .idCategoria(recorrente.getIdCategoria())
//        .build();
    return null;
  }

  public static List<TransacaoDTO> toTransacaoDTOList(List<RecorrenteEntity> recorrentes){
    return recorrentes.stream()
        .map(RecorrenteMapper::toTransacaoDTO)
        .collect(Collectors.toList());
  }
}
