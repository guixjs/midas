package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.CartaoEntity;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CriarTransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;

  @Autowired
  private CategoriaRepository categoriaRepository;

  @Autowired
  private ContaRepository contaRepository;

  @Autowired
  private CartaoRepository cartaoRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  private TransacaoEntity execute(TransacaoEntity transacao){
    return this.transacaoRepository.save(transacao);
  }

  public TransacaoResponseDTO criarTransacao(TransacaoDTO transacaoDTO, UUID idUsuario){
    CategoriaEntity categoriaEntity = null;
    CartaoResponseResumidoDTO cartaoResponse = null;
    CategoriaResponseResumidoDTO categoriaResponse = null;
    ContaEntity contaGeral;
    CartaoEntity cartao = null;

    BigDecimal valor = transacaoDTO.getValor();
    Integer idConta = transacaoDTO.getIdConta();
    System.out.println(idConta);

    if(transacaoDTO.getTipoTransacao() == TipoTransacao.DEBITO){
      valor = valor.negate();
    }


    if(transacaoDTO.getIdCategoria()!= null){
      categoriaEntity = this.categoriaRepository.findById(transacaoDTO.getIdCategoria())
          .orElseThrow(()-> new RuntimeException("Categoria não encontrada"));
    }

    var usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (idConta == null) {
      contaGeral = contaRepository.findByIdUsuarioAndNome(idUsuario,"Geral")
          .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta Geral não encontrada"));
      idConta = contaGeral.getId();

    } else {
      contaGeral = contaRepository.findByIdAndIdUsuario(idConta, idUsuario)
          .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta informada não encontrada para este usuário"));
      idConta = contaGeral.getId();
    }
    if(transacaoDTO.getIdCartao() != null){
      cartao = cartaoRepository.findByIdAndIdContaAndIdUsuario(transacaoDTO.getIdCartao(),idConta,idUsuario)
          .orElseThrow(()-> new OperacaoNaoPermitidaException("Cartão não encontrado"));
      if(!cartao.getIdConta().equals(idConta)){
        throw new OperacaoNaoPermitidaException("O cartão informado não encontrado para essa conta");
      }
    }

    var transacao = TransacaoEntity.builder()
        .descricao(transacaoDTO.getDescricao())
        .dataTransacao(transacaoDTO.getDataTransacao())
        .valor(valor)
        .tipoTransacao(transacaoDTO.getTipoTransacao())
        .categoria(categoriaEntity)
        .idUsuario(idUsuario)
        .idConta(idConta)
        .usuario(usuario)
        .cartao(cartao)
        .conta(contaGeral)
        .idCartao(transacaoDTO.getIdCartao())
        .build();

    if (transacaoDTO.getIdCartao() != null) {
      cartao = cartaoRepository.findById(transacaoDTO.getIdCartao())
          .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

      transacao.setCartao(cartao); // se usar builder e quiser setar depois
    }

    var resultado = execute(transacao);

    ContaResponseResumidoDTO contaResponse = ContaResponseResumidoDTO.builder()
        .nome(resultado.getConta().getNome())
        .banco(resultado.getConta().getBanco())
        .tipoConta(resultado.getConta().getTipoConta())
        .build();

    UsuarioResponseResumidoDTO usuarioResponse = UsuarioResponseResumidoDTO.builder()
        .nome(resultado.getUsuario().getNome())
        .email(resultado.getUsuario().getEmail())
        .telefone(resultado.getUsuario().getTelefone())
        .build();

    if(resultado.getCategoria()!=null){
      categoriaResponse = CategoriaResponseResumidoDTO.builder()
          .id(resultado.getCategoria().getId())
          .nome(resultado.getCategoria().getNome())
          .descricao(resultado.getCategoria().getDescricao())
          .build();
    }
    if(resultado.getCartao()!=null){
      cartaoResponse = CartaoResponseResumidoDTO.builder()
          .nome(resultado.getCartao().getNome())
          .dataVencimento(resultado.getCartao().getDataVencimento())
          .build();
    }

    return TransacaoResponseDTO.builder()
        .descricao(resultado.getDescricao())
        .dataTransacao(resultado.getDataTransacao())
        .valor(resultado.getValor())
        .tipoTransacao(resultado.getTipoTransacao())
        .conta(contaResponse)
        .cartao(cartaoResponse)
        .usuario(usuarioResponse)
        .categoria(categoriaResponse)
        .build();
  }

}
