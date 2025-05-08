package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaMaiorGastoDTO;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class IdentificarCategoriaMaiorGastoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public CategoriaMaiorGastoDTO execute(UUID idUsuario) {
        try {
            // Buscar categorias com despesas
            List<Object[]> categorias = transacaoRepository.findCategoriasComMaiorGasto(idUsuario);
            
            if (categorias == null || categorias.isEmpty()) {
                return new CategoriaMaiorGastoDTO("Nenhuma categoria", BigDecimal.ZERO, 0L, 0.0);
            }
            
            // Processar os dados da primeira categoria (maior gasto)
            Object[] categoria = categorias.get(0);
            String nome = categoria[0].toString();
            BigDecimal valor = new BigDecimal(categoria[1].toString());
            Long quantidade = Long.valueOf(categoria[2].toString());
            
            // Calcular percentual
            BigDecimal totalDespesas = transacaoRepository.somaTotalDespesasPorUsuario(idUsuario);
            double percentual = 0.0;
            
            if (totalDespesas != null && totalDespesas.compareTo(BigDecimal.ZERO) > 0) {
                percentual = valor.doubleValue() / totalDespesas.doubleValue() * 100;
            }
            
            return new CategoriaMaiorGastoDTO(nome, valor, quantidade, percentual);
        } catch (Exception e) {
            // Log do erro
            e.printStackTrace();
            return new CategoriaMaiorGastoDTO("Erro ao processar", BigDecimal.ZERO, 0L, 0.0);
        }
    }
}