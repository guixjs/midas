package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.dto.categoria.CategoriaResponseDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseDTO;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarContaService {

  @Autowired
  private ContaRepository contaRepository;

  public ContaResponseDTO execute(ContaEntity conta){
    ContaEntity resultado = this.contaRepository.save(conta);

    return ContaResponseDTO.builder()
        .nome(resultado.getNome())
        .tipoConta(resultado.getTipoConta())
        .banco(resultado.getBanco())
        .build();
  }
}
