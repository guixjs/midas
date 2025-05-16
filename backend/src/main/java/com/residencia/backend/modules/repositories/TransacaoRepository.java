package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.dto.dashboard.CategoriaValorDTO;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.dto.transacao.PrincipalDespesaDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID>, JpaSpecificationExecutor<TransacaoEntity> {
  Optional<TransacaoEntity> findByIdAndIdUsuario(UUID uuid, UUID idUsuario);

  // 1. Buscar as 10 maiores despesas por valor de um usuário (ordenado DESC para pegar as maiores)
  @Query("SELECT new com.residencia.backend.modules.dto.transacao.PrincipalDespesaDTO(t.descricao, t.valor, t.dataTransacao, c.nome, co.nome) " +
      "FROM TransacaoEntity t " +
      "JOIN t.categoria c " +
      "JOIN t.conta co " +
      "WHERE t.usuario.id = :usuarioId AND t.tipoTransacao = 'DESPESA' " +
      "ORDER BY t.valor DESC")
  List<PrincipalDespesaDTO> findTop10DespesasByValor(@Param("usuarioId") UUID usuarioId, Pageable pageable);

  @Query("""
    SELECT new com.residencia.backend.modules.dto.dashboard.CategoriaValorDTO(
        c.nome,
        SUM(t.valor)
    )
    FROM TransacaoEntity t
    JOIN t.categoria c
    WHERE t.usuario.id = :idUsuario
        AND t.tipoTransacao = 'DESPESA'
        AND MONTH(t.dataTransacao) = :mes
        AND YEAR(t.dataTransacao) = :ano
    GROUP BY c.nome
    ORDER BY SUM(t.valor) ASC
""")
  List<CategoriaValorDTO> buscarGastosPorCategoriaNoMes(UUID idUsuario, int mes, int ano);

  //esse deixa
  @Query("SELECT SUM(t.valor) FROM TransacaoEntity t WHERE t.conta.id = :idConta AND t.tipoTransacao = 'RECEITA'")
  BigDecimal somaReceitasPorConta(@Param("idConta") Integer idConta);
  //esse deixa
  @Query("SELECT SUM(t.valor) FROM TransacaoEntity t WHERE t.conta.id = :idConta AND t.tipoTransacao = 'DESPESA'")
  BigDecimal somaDespesasPorConta(@Param("idConta") Integer idConta);

  // 2. Soma total de receitas por usuário
  @Query("""
        SELECT COALESCE(SUM(t.valor), 0)
        FROM TransacaoEntity t
        WHERE t.usuario.id = :usuarioId AND t.tipoTransacao = 'RECEITA'
        """)
  BigDecimal somaTotalReceitasPorUsuario(@Param("usuarioId") UUID usuarioId);

  // 3. Soma total de despesas por usuário
  @Query("""
        SELECT COALESCE(SUM(t.valor), 0)
        FROM TransacaoEntity t
        WHERE t.usuario.id = :usuarioId AND t.tipoTransacao = 'DESPESA'
        """)
  BigDecimal somaTotalDespesasPorUsuario(@Param("usuarioId") UUID usuarioId);

  // 4. Contar transações por tipo e mês (usando FUNCTION para compatibilidade com JPA)
  @Query("""
        SELECT COUNT(t)
        FROM TransacaoEntity t
        WHERE t.usuario.id = :usuarioId
          AND t.tipoTransacao = :tipo
          AND FUNCTION('MONTH', t.dataTransacao) = :mes
          AND FUNCTION('YEAR', t.dataTransacao) = :ano
        """)
  Long contarTransacoesPorTipoEMes(
      @Param("usuarioId") UUID usuarioId,
      @Param("tipo") TipoTransacao tipo,
      @Param("mes") int mes,
      @Param("ano") int ano);
}
