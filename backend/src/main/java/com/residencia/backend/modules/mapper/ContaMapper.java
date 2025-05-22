package com.residencia.backend.modules.mapper;

import com.residencia.backend.modules.dto.conta.ContaDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.models.UsuarioEntity;

import java.util.UUID;

public class ContaMapper {

  public static ContaEntity toEntity(ContaDTO contaDTO, UUID idUsuario){
    return ContaEntity.builder()
          .nome(contaDTO.getNome())
          .banco(contaDTO.getBanco())
          .cor(contaDTO.getCor())
          .tipoConta(contaDTO.getTipoConta())
          .idUsuario(idUsuario)
          .build();

  }

  public static ContaResponseDTO toResponseDTO(ContaEntity resultado, UsuarioEntity usuario){
    UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(usuario);

    return ContaResponseDTO.builder()
        .idConta(resultado.getId())
        .nome(resultado.getNome())
        .cor(resultado.getCor())
        .tipoConta(resultado.getTipoConta())
        .banco(resultado.getBanco())
        .usuario(usuarioResponse)
        .build();
  }

  public static ContaResponseResumidoDTO toResponseResumidoDTO(ContaEntity contaGeral){
    return ContaResponseResumidoDTO.builder()
        .idConta(contaGeral.getId())
        .nome(contaGeral.getNome())
        .cor(contaGeral.getCor())
        .banco(contaGeral.getBanco())
        .tipoConta(contaGeral.getTipoConta())
        .build();
  }
}
