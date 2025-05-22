package com.residencia.backend.modules.mapper;

import com.residencia.backend.modules.dto.categoria.CategoriaDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.models.CategoriaEntity;

import java.util.UUID;

public class CategoriaMapper {

  public static CategoriaEntity toEntity(CategoriaDTO categoriaDTO, UUID idUsuario){
    return CategoriaEntity.builder()
        .descricao(categoriaDTO.getDescricao())
        .nome(categoriaDTO.getNome())
        .cor(categoriaDTO.getCor())
        .tipoTransacao(categoriaDTO.getTipoTransacao())
        .idUsuario(idUsuario)
        .build();
  }

  public static CategoriaResponseDTO toResponseDTO(CategoriaEntity resultado, UsuarioResponseResumidoDTO usuarioResponse){
    return CategoriaResponseDTO.builder()
        .id(resultado.getId())
        .nome(resultado.getNome())
        .descricao(resultado.getDescricao())
        .tipoTransacao(resultado.getTipoTransacao())
        .cor(resultado.getCor())
        .usuario(usuarioResponse)
        .build();
  }

  public static CategoriaResponseResumidoDTO toResponseResumidoDTO(CategoriaEntity categoria){
    if(categoria == null){
      return null;
    }
    return CategoriaResponseResumidoDTO.builder()
        .id(categoria.getId())
        .nome(categoria.getNome())
        .descricao(categoria.getDescricao())
        .tipoTransacao(categoria.getTipoTransacao())
        .cor(categoria.getCor())
        .build();
  }
}
