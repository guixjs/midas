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