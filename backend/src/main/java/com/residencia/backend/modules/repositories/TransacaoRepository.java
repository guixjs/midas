package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID> {
}
