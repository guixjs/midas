package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.dto.conta.ContaResponseDTO;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarContaService {

  @Autowired
  private ContaRepository contaRepository;

  public ContaResponseDTO execute(ContaEntity conta, UUID idUsuario) {
    boolean contaExistente = contaRepository.existsByIdUsuarioAndNome(idUsuario,conta.getNome());
    if (contaExistente) {
      throw new RuntimeException("Você já possui uma conta com esse nome.");
    }

    conta.setIdUsuario(idUsuario);
    ContaEntity resultado = contaRepository.save(conta);

    return ContaResponseDTO.builder()
        .nome(resultado.getNome())
        .tipoConta(resultado.getTipoConta())
        .banco(resultado.getBanco())
        .build();
  }
}
