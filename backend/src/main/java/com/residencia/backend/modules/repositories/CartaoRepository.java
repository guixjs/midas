package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CartaoRepository extends JpaRepository<CartaoEntity, UUID> {
    boolean existsByIdUsuarioAndNome(UUID idUsuario, String nome);

    Optional<CartaoEntity> findByIdAndIdContaAndIdUsuario(UUID id, Integer idConta, UUID idUsuario);
}
