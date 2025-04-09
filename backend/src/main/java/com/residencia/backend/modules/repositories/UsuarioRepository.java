package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
  Optional<UsuarioEntity> findByCpfOrEmail(String cpf, String email);
}
