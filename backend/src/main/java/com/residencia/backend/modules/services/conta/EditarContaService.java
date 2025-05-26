package com.residencia.backend.modules.services.conta;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.backend.modules.dto.conta.ContaDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.mapper.ContaMapper;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.ContaRepository;
import com.residencia.backend.modules.repositories.UsuarioRepository;

@Service
public class EditarContaService {


  @Autowired
  private ContaRepository contaRepository;
  @Autowired
  private UsuarioRepository usuarioRepository;

  public ContaResponseDTO execute(Integer id, ContaDTO contaDTO, UUID idUsuario){
    ContaEntity conta = contaRepository.findByIdAndIdUsuario(id, idUsuario)
    .orElseThrow(()-> new OperacaoNaoPermitidaException("Conta não encontrada"));

    contaRepository.findByIdUsuarioAndNome(idUsuario,contaDTO.getNome())
      .ifPresent(con -> {
        if (!con.getId().equals(id)) {
          throw new OperacaoNaoPermitidaException("Já existe uma conta com este nome");
        }
      });

      conta.setNome(contaDTO.getNome());
      conta.setBanco(contaDTO.getBanco());
      conta.setTipoConta(contaDTO.getTipoConta());
      conta.setCor(contaDTO.getCor());

      ContaEntity resultado = contaRepository.save(conta);

      UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(()-> new OperacaoNaoPermitidaException("Usuário não encontrada"));

      return ContaMapper.toResponseDTO(resultado, usuario);
  }
  
}
