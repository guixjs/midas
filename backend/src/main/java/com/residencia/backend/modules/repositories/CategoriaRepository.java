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

  @Query(value = "SELECT new com.residencia.backend.modules.dto.categoria.CategoriaMaiorGastoDTO(" +
      "c.id, c.nome, SUM(t.valor)) " +
      "FROM TransacaoEntity t " +
      "JOIN CategoriaEntity c ON t.idCategoria = c.id " +
      "WHERE t.idUsuario = :idUsuario " +
      "AND t.tipoTransacao = com.residencia.backend.modules.enums.TipoTransacao.DESPESA " +
      "GROUP BY c.id, c.nome " +
      "ORDER BY SUM(t.valor) DESC")
  CategoriaMaiorGastoDTO findCategoriaMaiorGasto(@Param("idUsuario") UUID idUsuario);

}

