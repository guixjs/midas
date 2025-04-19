package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaResponseDTO;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarCategoriaService {

  @Autowired
  private CategoriaRepository categoriaRepository;

  public CategoriaResponseDTO execute(CategoriaEntity categoria){
    String nome;
    nome = categoria.getNome().toLowerCase();
    nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
    categoria.setNome(nome);

    CategoriaEntity resultado = categoriaRepository.save(categoria);


    return CategoriaResponseDTO.builder()
        .id(resultado.getId())
        .nome(resultado.getNome())
        .descricao(resultado.getDescricao())
        .build();
  }
}
