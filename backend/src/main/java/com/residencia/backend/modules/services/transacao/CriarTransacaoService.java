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
import com.residencia.backend.modules.validator.TransacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CriarTransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private TransacaoValidator transacaoValidator;

  private TransacaoEntity execute(TransacaoEntity transacao){
    return this.transacaoRepository.save(transacao);
  }

  public TransacaoResponseDTO criarTransacao(TransacaoDTO transacaoDTO, UUID idUsuario){
    CategoriaEntity categoriaEntity = null;
    CartaoResponseResumidoDTO cartaoResponse = null;
    CategoriaResponseResumidoDTO categoriaResponse = null;
    CartaoEntity cartao = null;

    BigDecimal valor = transacaoDTO.getValor();

    if(transacaoDTO.getTipoTransacao() == TipoTransacao.DEBITO){
      valor = valor.negate();
    }

    var usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if(transacaoDTO.getIdCategoria()!= null){
      categoriaEntity = transacaoValidator.validarCategoria(transacaoDTO.getIdCategoria(), idUsuario);
    } // validacao categoria (se informada)



    Integer idConta = transacaoValidator.validarConta(transacaoDTO.getIdConta(), idUsuario);
    ContaEntity contaGeral = transacaoValidator.getContaById(idConta);

    if (transacaoDTO.getIdCartao() != null) {
      cartao = transacaoValidator.validarCartao(transacaoDTO.getIdCartao(), idConta, idUsuario);
    } // validacao cartao (se informado)



    var transacao = TransacaoEntity.builder()
        .descricao(transacaoDTO.getDescricao())
        .dataTransacao(transacaoDTO.getDataTransacao())
        .valor(valor)
        .tipoTransacao(transacaoDTO.getTipoTransacao())
        .idUsuario(idUsuario)
        .idConta(idConta)
        .idCartao(transacaoDTO.getIdCartao())
        .cartao(cartao)
        .categoria(categoriaEntity)
        .conta(contaGeral)
        .usuario(usuario)
        .build();

    var resultado = execute(transacao);

    ContaResponseResumidoDTO contaResponse = ContaResponseResumidoDTO.builder()
        .id(resultado.getIdConta())
        .nome(resultado.getConta().getNome())
        .banco(resultado.getConta().getBanco())
        .tipoConta(resultado.getConta().getTipoConta())
        .build();

    UsuarioResponseResumidoDTO usuarioResponse = UsuarioResponseResumidoDTO.builder()
        .id(resultado.getUsuario().getId())
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
          .id(resultado.getIdCartao())
          .nome(resultado.getCartao().getNome())
          .dataVencimento(resultado.getCartao().getDataVencimento())
          .build();
    }

    return TransacaoResponseDTO.builder()
        .id(resultado.getId())
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
