package com.residencia.backend.modules.services.dashboard;

import com.residencia.backend.modules.dto.dashboard.CategoriaMaiorGastoDTO;
import com.residencia.backend.modules.dto.dashboard.CategoriaValorDTO;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoriaMaiorGastoService {

  @Autowired
  private TransacaoRepository transacaoRepository;

  public List<CategoriaMaiorGastoDTO> montarCategoriasMaisGastas(UUID idUsuario, YearMonth mesAtual) {
    int mes = mesAtual.getMonthValue();
    int ano = mesAtual.getYear();

    List<CategoriaValorDTO> valores = transacaoRepository.buscarGastosPorCategoriaNoMes(idUsuario, mes, ano);

    BigDecimal totalGasto = valores.stream()
        .map(CategoriaValorDTO::getValor)
        .map(BigDecimal::abs)
        .reduce(BigDecimal.ZERO, BigDecimal::add);


    return valores.stream()
        .limit(5)
        .map(cat -> {
          BigDecimal valorAbsoluto = cat.getValor().abs();
          BigDecimal percentual = totalGasto.compareTo(BigDecimal.ZERO) > 0
              ? valorAbsoluto.multiply(BigDecimal.valueOf(100)).divide(totalGasto.abs(), 2, RoundingMode.HALF_UP)
              : BigDecimal.ZERO;

          return new CategoriaMaiorGastoDTO(cat.getNomeCategoria(), valorAbsoluto, percentual,cat.getCor());
        })
        .collect(Collectors.toList());
  }
}
