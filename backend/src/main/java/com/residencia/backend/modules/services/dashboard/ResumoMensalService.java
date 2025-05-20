package com.residencia.backend.modules.services.dashboard;

import com.residencia.backend.modules.dto.dashboard.ResumoMensalDTO;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ResumoMensalService {
  @Autowired
  private TransacaoRepository transacaoRepository;

  public List<ResumoMensalDTO> montarListaResumo(UUID idUsuario, Integer qtdMeses){
    YearMonth mesAtual = YearMonth.now();
    YearMonth mesInicial = mesAtual.minusMonths(qtdMeses - 1);
    LocalDate dataInicial = mesInicial.atDay(1);

    List<ResumoMensalDTO> listaResumosMensais = transacaoRepository.buscarResumoMensal(idUsuario,dataInicial);

    Map<YearMonth,ResumoMensalDTO> mapaResumoPorMes = listaResumosMensais.stream()
        .collect(Collectors.toMap(ResumoMensalDTO::getMes, Function.identity()));

    List<ResumoMensalDTO> listaResumoCompleta = new ArrayList<>();

    for(int i = 0; i < qtdMeses; i++){
      YearMonth mesCorrespondente = mesInicial.plusMonths(i);
      ResumoMensalDTO resumo = mapaResumoPorMes.get(mesCorrespondente);

      if(resumo==null){
        resumo = new ResumoMensalDTO(
            mesCorrespondente.getYear(),
            mesCorrespondente.getMonthValue(),
            0L,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO);
      }
      listaResumoCompleta.add(resumo);
    }
    return listaResumoCompleta;
  }
}
