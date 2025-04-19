package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.enums.TipoConta;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarContaGeralService {

  @Autowired
  private ContaRepository contaRepository;

  public void criarContaGeral(UsuarioEntity usuario){
    var conta = this.contaRepository.findByIdUsuarioAndNome(usuario.getId(), "Geral");

    if (conta.isEmpty()) {
      ContaEntity contaGeral = ContaEntity.builder()
          .usuarioEntity(usuario)
          .idUsuario(usuario.getId())
          .nome("Geral")
          .tipoConta(TipoConta.CARTEIRA)
          .build();

      contaRepository.save(contaGeral);
    }

  }
}
