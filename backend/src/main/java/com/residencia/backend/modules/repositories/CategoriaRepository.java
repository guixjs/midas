package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity,Integer> {
  Optional<CategoriaEntity>findById(Integer id);

  @Query("SELECT c FROM categoria c WHERE c.idUsuario IS NULL OR c.idUsuario = :idUsuario")
  List<CategoriaEntity> findCategoriasDisponiveis(UUID idUsuario);


   Optional<CategoriaEntity> findByNomeAndIdUsuario(String nome, UUID idUsuario);

}
