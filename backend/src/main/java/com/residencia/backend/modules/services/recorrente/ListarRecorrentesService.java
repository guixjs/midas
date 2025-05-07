package com.residencia.backend.modules.services.recorrente;

import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.models.RecorrenteEntity;
import com.residencia.backend.modules.repositories.RecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarRecorrentesService {

  @Autowired
  private RecorrenteRepository recorrenteRepository;
  @Autowired
  private ConverterRecorrenteEmTransacaoDTOService converter;

  public List<TransacaoDTO> execute(UUID idUser){
    List<RecorrenteEntity> recorrentes = recorrenteRepository.findAllByIdUsuario(idUser);
    return converter.converter(recorrentes);
  }
}
