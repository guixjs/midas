package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.dto.categoria.CategoriaMaiorGastoDTO;
import com.residencia.backend.modules.models.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {

  Optional<CategoriaEntity> findById(Integer id);

  @Query("SELECT c FROM categoria c WHERE c.id = :id AND (c.idUsuario IS NULL OR c.idUsuario = :idUsuario)")
  Optional<CategoriaEntity> findByIdAndPossivelUsuario(@Param("id") Integer id, @Param("idUsuario") UUID idUsuario);

  @Query("SELECT c FROM categoria c WHERE c.idUsuario IS NULL OR c.idUsuario = :idUsuario")
  List<CategoriaEntity> findCategoriasDisponiveis(@Param("idUsuario") UUID idUsuario);

  Optional<CategoriaEntity> findByNomeAndIdUsuario(String nome, UUID idUsuario);

  @Query(value = "SELECT new CategoriaMaiorGastoDTO(" +
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

