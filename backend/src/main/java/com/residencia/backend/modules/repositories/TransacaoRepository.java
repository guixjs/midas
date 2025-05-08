package com.residencia.backend.modules.repositories;

import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.dto.categoria.CategoriaMaiorGastoDTO;
import com.residencia.backend.modules.dto.transacao.PrincipalDespesaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID>, JpaSpecificationExecutor<TransacaoEntity> {

    Optional<TransacaoEntity> findByIdAndIdUsuario(UUID id, UUID idUsuario);
    
    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM TransacaoEntity t WHERE t.idUsuario = :idUsuario AND t.tipoTransacao = 'RECEITA'")
    BigDecimal somaTotalReceitasPorUsuario(@Param("idUsuario") UUID idUsuario);

    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM TransacaoEntity t WHERE t.idUsuario = :idUsuario AND t.tipoTransacao = 'DESPESA'")
    BigDecimal somaTotalDespesasPorUsuario(@Param("idUsuario") UUID idUsuario);

    @Query("SELECT COUNT(t) FROM TransacaoEntity t WHERE t.idUsuario = :idUsuario AND t.tipoTransacao = :tipo " +
       "AND MONTH(t.dataTransacao) = :mes AND YEAR(t.dataTransacao) = :ano")
    Long contarTransacoesPorTipoEMes(
        @Param("idUsuario") UUID idUsuario,
        @Param("tipo") String tipo,
        @Param("mes") Integer mes,
        @Param("ano") Integer ano
    );
    
    @Query("SELECT new com.residencia.backend.modules.dto.transacao.PrincipalDespesaDTO(" +
    "t.descricao, t.valor, t.dataTransacao, c.nome, co.nome) " +
    "FROM TransacaoEntity t " +
    "JOIN t.categoria c " +
    "JOIN t.conta co " +
    "WHERE t.idUsuario = :idUsuario " +
    "AND t.tipoTransacao = 'DESPESA' " +
    "ORDER BY t.valor DESC")
    List<PrincipalDespesaDTO> findTop10DespesasByValor(@Param("idUsuario") UUID idUsuario);

    @Query("SELECT c.nome as nome, SUM(t.valor) as valorTotal, COUNT(t) as quantidade " +
       "FROM TransacaoEntity t " +
       "JOIN t.categoria c " +
       "WHERE t.idUsuario = :idUsuario " +
       "AND t.tipoTransacao = 'DESPESA' " +
       "GROUP BY c.nome " +
       "ORDER BY SUM(t.valor) DESC")
    List<Object[]> findCategoriasComMaiorGasto(@Param("idUsuario") UUID idUsuario);
}
