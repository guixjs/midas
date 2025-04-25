package com.residencia.backend.modules.mapper;

import com.residencia.backend.modules.dto.cartao.CartaoDTO;
import com.residencia.backend.modules.dto.cartao.CartaoResponseDTO;
import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.models.CartaoEntity;

import java.util.UUID;

public class CartaoMapper {
  public static CartaoEntity toEntity(CartaoDTO cartaoDTO, UUID idUsuario, Integer idConta) {
    return CartaoEntity.builder()
        .nome(cartaoDTO.getNome())
        .dataVencimento(cartaoDTO.getDataVencimento())
        .idConta(idConta)
        .idUsuario(idUsuario)
        .build();
  }

  public static CartaoResponseDTO toResponseDTO(CartaoEntity resultado, ContaResponseResumidoDTO contaResponse, UsuarioResponseResumidoDTO usuarioResponse) {
    return CartaoResponseDTO.builder()
        .id(resultado.getId())
        .nome(resultado.getNome())
        .dataVencimento(resultado.getDataVencimento())
        .conta(contaResponse)
        .usuario(usuarioResponse)
        .build();
  }

  public static CartaoResponseResumidoDTO toResponseResumidoDTO(CartaoEntity cartao){
    if(cartao == null){
      return null;
    }
    return CartaoResponseResumidoDTO.builder()
        .id(cartao.getId())
        .nome(cartao.getNome())
        .dataVencimento(cartao.getDataVencimento())
        .build();
  }
}
