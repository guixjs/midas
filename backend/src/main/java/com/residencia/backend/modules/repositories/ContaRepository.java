package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.ContaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<ContaEntity, Integer>{
  Optional<ContaEntity> findByIdUsuarioAndNome(UUID usuario,String nome);
}
