package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.enums.TipoConta;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CriarContaPadraoService {

  @Autowired
  private ContaRepository contaRepository;

  public void criarContaPadrao(UsuarioEntity usuario){
    var conta = this.contaRepository.findByIdUsuarioAndNome(usuario.getId(), "Padrão");
    BigDecimal saldo = BigDecimal.ZERO;

    if (conta.isEmpty()) {
      ContaEntity contaPadrao = ContaEntity.builder()
          .usuarioEntity(usuario)
          .idUsuario(usuario.getId())
          .nome("Padrão")
          .cor("#389111")
          .saldo(saldo)
          .tipoConta(TipoConta.CARTEIRA)
          .banco("Conta criada automaticamente pelo sistema")
          .build();

      contaRepository.save(contaPadrao);
    }

  }
}
