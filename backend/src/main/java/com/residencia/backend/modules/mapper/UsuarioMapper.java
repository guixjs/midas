package com.residencia.backend.modules.mapper;

import com.residencia.backend.modules.dto.usuario.UsuarioDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.models.UsuarioEntity;

public class UsuarioMapper {

  public static UsuarioEntity toEntity(UsuarioDTO usuarioDTO){
    return UsuarioEntity.builder()
        .nome(usuarioDTO.getNome())
        .cpf(usuarioDTO.getCpf())
        .email(usuarioDTO.getEmail())
        .telefone(usuarioDTO.getTelefone())
        .senha(usuarioDTO.getSenha())
        .build();
  }

  public static UsuarioResponseDTO toResponseDTO(UsuarioEntity usuarioEntity){
    return UsuarioResponseDTO.builder()
        .id(usuarioEntity.getId())
        .nome(usuarioEntity.getNome())
        .cpf(usuarioEntity.getCpf())
        .email(usuarioEntity.getEmail())
        .telefone(usuarioEntity.getTelefone())
        .senha(usuarioEntity.getSenha())
        .build();
  }

  public static UsuarioResponseResumidoDTO toResponseResumidoDTO(UsuarioEntity usuarioEntity){
    return UsuarioResponseResumidoDTO.builder()
        .id(usuarioEntity.getId())
        .nome(usuarioEntity.getNome())
        .email(usuarioEntity.getEmail())
        .telefone(usuarioEntity.getTelefone())
        .build();
  }
}
