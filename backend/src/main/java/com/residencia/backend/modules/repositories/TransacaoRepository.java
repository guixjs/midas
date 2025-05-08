package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.dto.categoria.CategoriaMaiorGastoDTO;
import com.residencia.backend.modules.models.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID>, JpaSpecificationExecutor<TransacaoEntity> {
  Optional<TransacaoEntity> findByIdAndIdUsuario(UUID uuid, UUID idUsuario);

//  @Query("SELECT new com.residencia.backend.modules.dto.categoria.CategoriaMaiorGastoDTO(" +
//          "c.nome, " +
//          "SUM(t.valor), " +
//          "COUNT(t), " +
//          "(SUM(t.valor) / (SELECT SUM(t2.valor) FROM transacao t2 WHERE t2.idUsuario = :idUsuario AND t2.tipo = 'DESPESA')) * 100) " +
//          "FROM transacao t " +
//          "JOIN t.categoria c " +
//          "WHERE t.idUsuario = :idUsuario " +
//          "AND t.tipo = 'DESPESA' " +
//          "GROUP BY c.nome " +
//          "ORDER BY SUM(t.valor) DESC " +
//          "LIMIT 1")
@Query(value = "SELECT new com.residencia.backend.modules.dto.categoria.CategoriaMaiorGastoDTO(" +
        "c.nome, SUM(t.valor), COUNT(t.*), " +
        "(SUM(t.valor) / (SELECT SUM(t2.valor) FROM transacao t2 WHERE t2.id_usuario = :idUsuario AND t2.tipo = 'DESPESA')) * 100) " +
        "FROM transacao t " +
        "JOIN categoria c ON t.categoria_id = c.id " +
        "WHERE t.id_usuario = :idUsuario AND t.tipo = 'DESPESA' " +
        "GROUP BY c.nome " +
        "ORDER BY SUM(t.valor) DESC " +
        "LIMIT 1", nativeQuery = true)
  CategoriaMaiorGastoDTO findCategoriaMaiorGasto(@Param("idUsuario") UUID idUsuario);
}
