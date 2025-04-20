package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface CartaoRepository extends JpaRepository<CartaoEntity, UUID> {
}
