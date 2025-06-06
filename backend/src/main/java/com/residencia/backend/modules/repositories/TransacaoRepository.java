package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.dto.dashboard.CategoriaValorDTO;
import com.residencia.backend.modules.dto.dashboard.ResumoMensalDTO;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.enums.TipoTransacao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID>, JpaSpecificationExecutor<TransacaoEntity> {
  Optional<TransacaoEntity> findByIdAndIdUsuario(UUID uuid, UUID idUsuario);
  List<TransacaoEntity> findByUsuarioIdOrderByDataCriacaoDesc(UUID usuarioId, Pageable pageable);

  @Query("""
    SELECT t FROM TransacaoEntity t
    WHERE t.idUsuario = :idUsuario
        AND EXTRACT(MONTH FROM t.dataTransacao) = :mes
        AND EXTRACT(YEAR FROM t.dataTransacao) = :ano
        AND t.tipoTransacao = :tipo
    ORDER BY ABS(t.valor) DESC
  """)
  List<TransacaoEntity> buscarTopTransacoesPorTipoEMes(
      @Param("idUsuario") UUID idUsuario,
      @Param("tipo") TipoTransacao tipo,
      @Param("ano") int ano,
      @Param("mes") int mes,
      Pageable pageable);

  @Query("""
      SELECT SUM(t.valor) FROM TransacaoEntity t
      WHERE t.conta.id = :idConta
         AND t.tipoTransacao = 'RECEITA'
         AND EXTRACT(MONTH FROM t.dataTransacao) = :mes
         AND EXTRACT(YEAR FROM t.dataTransacao) = :ano
      """)
  BigDecimal somaReceitasPorContaMes(@Param("idConta") Integer idConta, @Param("mes") int mes, @Param("ano") int ano);

  @Query("""
      SELECT SUM(t.valor) FROM TransacaoEntity t
      WHERE t.conta.id = :idConta
         AND t.tipoTransacao = 'DESPESA'
         AND EXTRACT(MONTH FROM t.dataTransacao) = :mes
         AND EXTRACT(YEAR FROM t.dataTransacao) = :ano
      """)
  BigDecimal somaDespesasPorContaMes(@Param("idConta") Integer idConta, @Param("mes") int mes, @Param("ano") int ano);

  @Query("""
    SELECT new com.residencia.backend.modules.dto.dashboard.CategoriaValorDTO(
        c.nome,
        SUM(t.valor),
        c.cor
    )
    FROM TransacaoEntity t
    JOIN t.categoria c
    WHERE t.usuario.id = :idUsuario
        AND t.tipoTransacao = 'DESPESA'
        AND EXTRACT(MONTH FROM t.dataTransacao) = :mes
        AND EXTRACT(YEAR FROM t.dataTransacao) = :ano
    GROUP BY c.nome, c.cor
    ORDER BY SUM(t.valor) ASC
  """)
  List<CategoriaValorDTO> buscarGastosPorCategoriaNoMes(UUID idUsuario, int mes, int ano);

  @Query("""
    SELECT new com.residencia.backend.modules.dto.dashboard.ResumoMensalDTO(
      EXTRACT(YEAR FROM t.dataTransacao),
      EXTRACT(MONTH FROM t.dataTransacao),
      COUNT(t),
      SUM(t.valor),
      SUM(t.valor) FILTER (WHERE t.tipoTransacao = 'RECEITA'),
      SUM(t.valor) FILTER (WHERE t.tipoTransacao = 'DESPESA')
    )
    FROM TransacaoEntity t
    WHERE t.usuario.id = :idUsuario
        AND t.dataTransacao >= :dataInicial
    GROUP BY EXTRACT(YEAR FROM t.dataTransacao), EXTRACT(MONTH FROM t.dataTransacao)
    ORDER BY EXTRACT(YEAR FROM t.dataTransacao) DESC, EXTRACT(MONTH FROM t.dataTransacao) DESC
  """)
  List<ResumoMensalDTO> buscarResumoMensal(@Param("idUsuario") UUID idUsuario, @Param("dataInicial") LocalDate dataInicial);
}
