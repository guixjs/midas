package com.residencia.backend.modules.services.recorrente;

import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.RecorrenteEntity;
import com.residencia.backend.modules.repositories.RecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExcluirRecorrenteService {

  @Autowired
  private RecorrenteRepository recorrenteRepository;

  public void execute(UUID id, UUID idUsuario) {
    RecorrenteEntity recorrente = recorrenteRepository.findByIdAndIdUsuario(id, idUsuario)
        .orElseThrow(() -> new OperacaoNaoPermitidaException("Transação não encontrada"));
    recorrenteRepository.delete(recorrente);
  }
}
