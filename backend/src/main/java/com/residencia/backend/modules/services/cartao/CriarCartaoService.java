package com.residencia.backend.modules.services.cartao;

import com.residencia.backend.modules.dto.cartao.CartaoResponseDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.CartaoEntity;
import com.residencia.backend.modules.repositories.CartaoRepository;
import com.residencia.backend.modules.repositories.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class CriarCartaoService {

  @Autowired
  private CartaoRepository cartaoRepository;

  @Autowired
  private ContaRepository contaRepository;

  public CartaoResponseDTO execute(CartaoEntity cartaoEntity, UUID idUsuario){

    var conta = contaRepository.findByIdAndIdUsuario(cartaoEntity.getIdConta(), idUsuario)
        .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta não encontrada para este usuário"));

    if(conta.getNome().equals("Geral") ) {
      throw new OperacaoNaoPermitidaException("O cartão não pode ser vinculado à conta Geral");
    }

    boolean cartaoExistente = contaRepository.existsByIdUsuarioAndNome(idUsuario,conta.getNome());
    if (cartaoExistente) {
      throw new RuntimeException("Você já possui um cartão com esse nome.");
    }
    // Criar o cartão com vínculo à conta
    var cartao = CartaoEntity.builder()
        .nome(cartaoEntity.getNome())
        .dataVencimento(cartaoEntity.getDataVencimento())
        .idConta(cartaoEntity.getIdConta())
        .idUsuario(idUsuario)
        .build();
    CartaoEntity resultado = this.cartaoRepository.save(cartao);

    return CartaoResponseDTO.builder()
        .id(resultado.getId())
        .nome(resultado.getNome())
        .dataVencimento(resultado.getDataVencimento())
        .idConta(conta.getId())
        .idUsuario(resultado.getIdUsuario())
        .dataCriacao(resultado.getDataCriacao())
        .build();

  }
}
