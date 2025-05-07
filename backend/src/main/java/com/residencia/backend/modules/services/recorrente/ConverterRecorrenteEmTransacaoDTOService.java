package com.residencia.backend.modules.services.recorrente;

import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.mapper.RecorrenteMapper;
import com.residencia.backend.modules.models.RecorrenteEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConverterRecorrenteEmTransacaoDTOService {
  public List<TransacaoDTO> converter(List<RecorrenteEntity> recorrentes) {
    return RecorrenteMapper.toTransacaoDTOList(recorrentes);
  }
}
