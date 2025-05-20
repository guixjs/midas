package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.ContaEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<ContaEntity, Integer>{
  List<ContaEntity> findAllByIdUsuario(UUID idUsuario);
  Optional<ContaEntity> findById(Integer idConta);
  Optional<ContaEntity> findByIdUsuarioAndNome(UUID usuario,String nome);
  Optional<ContaEntity> findByIdAndIdUsuario(Integer id,UUID idUsuario);
  boolean existsByIdUsuarioAndNome(UUID idUsuario, String nome);
  List<ContaEntity> findByIdUsuario(UUID idUsuario);
}
