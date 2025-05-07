package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.RecorrenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecorrenteRepository extends JpaRepository<RecorrenteEntity, UUID> {
  List<RecorrenteEntity> findAllByIdUsuario(UUID idUsuario);
}
