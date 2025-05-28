package com.residencia.backend.modules.validator;

import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.CartaoEntity;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.repositories.CartaoRepository;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransacaoValidator {

  @Autowired
  private CartaoRepository cartaoRepository;

  @Autowired
  private CategoriaRepository categoriaRepository;

  @Autowired
  private ContaRepository contaRepository;

  public CategoriaEntity validarCategoria(Integer idCategoria, UUID idUsuario){
    if(idCategoria == null){
      return null;
    }
    return categoriaRepository.findByIdAndPossivelUsuario(idCategoria, idUsuario)
        .orElseThrow(() -> new OperacaoNaoPermitidaException("Categoria não encontrada"));
  } //retorna a categoria encontrada

  public CartaoEntity validarCartao(UUID idCartao, Integer idConta, UUID idUser){
    if(idCartao == null){
      return null;
    }
    var cartao = cartaoRepository.findByIdAndIdContaAndIdUsuario(idCartao, idConta, idUser)
        .orElseThrow(()-> new OperacaoNaoPermitidaException("Cartão não encontrado"));
    if(!cartao.getIdConta().equals(idConta)){
      throw new OperacaoNaoPermitidaException("Cartão não encontrado");
    }
    return cartao;
  } //retorna o cartao encontrado

  public Integer validarConta(Integer idConta, UUID user) {
    if (idConta == null) {
      return contaRepository.findByIdUsuarioAndNome(user, "Padrão")
          .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta Padrão não encontrada"))
          .getId();
    }
    return contaRepository.findByIdAndIdUsuario(idConta, user)
        .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta não encontrada"))
        .getId();
  } //retorna o ID da conta

  public ContaEntity getContaById(Integer idConta){
    return contaRepository.findById(idConta)
        .orElseThrow(()-> new RuntimeException("Conta não encontrada"));
  } //retorna a conta (usando o id da conta de cima)
}
