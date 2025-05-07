package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.mapper.CategoriaMapper;
import com.residencia.backend.modules.mapper.UsuarioMapper;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarCategoriaService {

  @Autowired
  private CategoriaRepository categoriaRepository;
  @Autowired
  private UsuarioRepository usuarioRepository;

  public CategoriaResponseDTO execute(CategoriaDTO categoriaDTO,UUID idUsuario){

    String nome = categoriaDTO.getNome().toLowerCase();
    nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
    categoriaDTO.setNome(nome);

    this.categoriaRepository.findByNomeAndIdUsuario(nome, idUsuario)
        .ifPresent((cat)->{
          throw new OperacaoNaoPermitidaException("Categoria já cadastrada");
        });


    CategoriaEntity categoria = CategoriaMapper.toEntity(categoriaDTO,idUsuario);

    CategoriaEntity resultado = categoriaRepository.save(categoria);

    UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(usuarioEntity);
    return CategoriaMapper.toResponseDTO(resultado,usuarioResponse);

  }
}
