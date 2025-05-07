package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.dto.conta.SaldoTotalDTO;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CalcularSaldoTotalService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public SaldoTotalDTO execute(UUID idUsuario) {
        BigDecimal totalReceitas = transacaoRepository.somaTotalReceitasPorUsuario(idUsuario);
        BigDecimal totalDespesas = transacaoRepository.somaTotalDespesasPorUsuario(idUsuario);
        
        BigDecimal saldoTotal = totalReceitas.subtract(totalDespesas);
        boolean saldoPositivo = saldoTotal.compareTo(BigDecimal.ZERO) >= 0;

        return SaldoTotalDTO.builder()
                .totalReceitas(totalReceitas)
                .totalDespesas(totalDespesas)
                .saldoTotal(saldoTotal)
                .saldoPositivo(saldoPositivo)
                .build();
    }
}
