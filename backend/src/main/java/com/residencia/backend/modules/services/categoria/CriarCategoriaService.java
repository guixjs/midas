package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarCategoriaService {

  @Autowired
  private CategoriaRepository categoriaRepository;

  public CategoriaEntity execute(CategoriaEntity categoria){
    return this.categoriaRepository.save(categoria);
  }
}
