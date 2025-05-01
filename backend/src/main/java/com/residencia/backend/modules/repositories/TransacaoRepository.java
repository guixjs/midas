package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID>, JpaSpecificationExecutor<TransacaoEntity> {
  Optional<TransacaoEntity> findByIdAndIdUsuario(UUID uuid, UUID idUsuario);
}
