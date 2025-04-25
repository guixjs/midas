package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.dto.conta.ContaDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseDTO;
import com.residencia.backend.modules.mapper.ContaMapper;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.ContaRepository;
import com.residencia.backend.modules.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarContaService {

  @Autowired
  private ContaRepository contaRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public ContaResponseDTO execute(ContaDTO contaDTO, UUID idUsuario) {
    boolean contaExistente = contaRepository.existsByIdUsuarioAndNome(idUsuario,contaDTO.getNome());

    if (contaExistente) {
      throw new RuntimeException("Você já possui uma conta com esse nome.");
    }

    UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    ContaEntity conta = ContaMapper.toEntity(contaDTO, idUsuario);
    ContaEntity resultado = contaRepository.save(conta);
    return ContaMapper.toResponseDTO(resultado,usuarioEntity);
  }
}
