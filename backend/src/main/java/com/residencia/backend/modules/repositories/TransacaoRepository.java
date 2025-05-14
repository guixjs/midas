package com.residencia.backend.modules.repositories;

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
  @Query(value = "SELECT new com.residencia.backend.modules.dto.transacao.PrincipalDespesaDTO(" +
    "t.descricao, t.valor, t.dataTransacao, c.nome, co.nome) " +
    "FROM TransacaoEntity t " +
    "JOIN t.categoria c " +
    "JOIN t.conta co " +
    "WHERE t.idUsuario = :idUsuario " +
    "AND t.tipoTransacao = 'DESPESA' " +
    "ORDER BY ABS(t.valor) DESC")
List<PrincipalDespesaDTO> findTop10DespesasByValor(@Param("idUsuario") UUID idUsuario, Pageable pageable);

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
      WHERE t.idUsuario = :usuarioId
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
