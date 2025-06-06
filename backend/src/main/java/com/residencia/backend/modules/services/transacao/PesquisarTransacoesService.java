package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.transacao.TransacaoPesquisaDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseResumidoDTO;
import com.residencia.backend.modules.mapper.TransacaoMapper;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PesquisarTransacoesService {

    @Autowired
    private TransacaoRepository transacaoRepository;


    public List<TransacaoResponseResumidoDTO> execute(TransacaoPesquisaDTO filtros, UUID idUsuario) {
        Specification<TransacaoEntity> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Filtro usuario (obrigatório)
            predicates.add(builder.equal(root.get("idUsuario"), idUsuario));

            if (filtros.getDataInicio() == null && filtros.getDataFim() == null) {
                YearMonth anoMes = filtros.getMesCorrente() != null ? filtros.getMesCorrente() : YearMonth.now();
                filtros.setDataInicio(anoMes.atDay(1));
                filtros.setDataFim(anoMes.atEndOfMonth());
            }
            
            // Filtro periodo
            if (filtros.getDataInicio() != null) {
                predicates.add(builder.greaterThanOrEqualTo(
                    root.get("dataTransacao"), filtros.getDataInicio()));
            }

            
            if (filtros.getDataFim() != null) {
                predicates.add(builder.lessThanOrEqualTo(
                    root.get("dataTransacao"), filtros.getDataFim()));
            }
            
            // Filtro categoria
            if (filtros.getIdCategoria() != null) {
                predicates.add(builder.equal(root.get("idCategoria"), filtros.getIdCategoria()));
            }

            if(filtros.getTipoTransacao() != null) {
                predicates.add(builder.equal(root.get("tipoTransacao"), filtros.getTipoTransacao()));
            }

            if (Boolean.TRUE.equals(filtros.isPossuiCartao())) {
                predicates.add(builder.isNotNull(root.get("idCartao")));
            }
            if(filtros.getIdConta() != null) {
                predicates.add(builder.equal(root.get("idConta"), filtros.getIdConta()));
            }

            if (filtros.getValorMin() != null) {
                predicates.add(builder.greaterThanOrEqualTo(
                    root.get("valor"), filtros.getValorMin()));
            }

            if (filtros.getValorMax() != null) {
                predicates.add(builder.lessThanOrEqualTo(
                    root.get("valor"), filtros.getValorMax()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        
        List<TransacaoEntity> transacoes = transacaoRepository.findAll(spec);
        List<TransacaoResponseResumidoDTO> listaRespostas = new ArrayList<>();
        
        for (TransacaoEntity transacao : transacoes) {
            TransacaoResponseResumidoDTO resposta = TransacaoMapper.toResponseResumidoDTO(transacao);
            listaRespostas.add(resposta);
        }
        return listaRespostas;
    }
}