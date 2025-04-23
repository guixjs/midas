package com.residencia.backend.modules.services.cartao;

import com.residencia.backend.modules.dto.cartao.CartaoResponseDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
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

    boolean cartaoExistente = cartaoRepository.existsByIdUsuarioAndNome(idUsuario,cartaoEntity.getNome());
    if (cartaoExistente) {
      throw new RuntimeException("Você já possui um cartão com esse nome.");
    }

    var cartao = CartaoEntity.builder()
        .nome(cartaoEntity.getNome())
        .dataVencimento(cartaoEntity.getDataVencimento())
        .idConta(conta.getId())
        .idUsuario(idUsuario)
        .build();

    CartaoEntity resultado = cartaoRepository.save(cartao);

    ContaResponseResumidoDTO contaResponse = ContaResponseResumidoDTO.builder()
        .id(conta.getId())
        .nome(conta.getNome())
        .banco(conta.getBanco())
        .tipoConta(conta.getTipoConta())
        .build();

    return CartaoResponseDTO.builder()
        .id(resultado.getId())
        .nome(resultado.getNome())
        .dataVencimento(resultado.getDataVencimento())
        .conta(contaResponse)
        .usuario(null)
        .build();
  }
}
