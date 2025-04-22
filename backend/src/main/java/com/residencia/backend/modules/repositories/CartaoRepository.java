package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.CartaoEntity;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface CartaoRepository extends JpaRepository<CartaoEntity, UUID> {
  Optional<CartaoEntity> findByIdAndIdContaAndIdUsuario(UUID idCartao, Integer idConta, UUID idUsuario);
  boolean existsByIdUsuarioAndNome(UUID idUsuario, String nome);

}
