package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.models.*;
import com.residencia.backend.modules.repositories.*;
import com.residencia.backend.modules.validator.TransacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
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
    CartaoResponseResumidoDTO cartaoResponse = null;
    CategoriaResponseResumidoDTO categoriaResponse = null;

    BigDecimal valor = transacaoDTO.getValor();
    LocalDate data = transacaoDTO.getDataTransacao();

    if(data == null){
      transacaoDTO.setDataTransacao(LocalDate.now());
    }

    if(transacaoDTO.getTipoTransacao() == TipoTransacao.DEBITO){
      valor = valor.negate();
    }

    UsuarioEntity usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    CategoriaEntity categoria = transacaoValidator.validarCategoria(transacaoDTO.getIdCategoria(), idUsuario);
    Integer idConta = transacaoValidator.validarConta(transacaoDTO.getIdConta(), idUsuario);
    ContaEntity contaGeral = transacaoValidator.getContaById(idConta);
    CartaoEntity cartao = transacaoValidator.validarCartao(transacaoDTO.getIdCartao(), idConta, idUsuario);


    var transacao = TransacaoEntity.builder()
        .descricao(transacaoDTO.getDescricao())
        .dataTransacao(transacaoDTO.getDataTransacao())
        .valor(valor)
        .tipoTransacao(transacaoDTO.getTipoTransacao())
        .idUsuario(idUsuario)
        .idConta(idConta)
        .idCategoria(Optional.ofNullable(categoria).map(CategoriaEntity::getId).orElse(null))
        .idCartao(Optional.ofNullable(cartao).map(CartaoEntity::getId).orElse(null))
        .build();

    var resultado = execute(transacao);

    ContaResponseResumidoDTO contaResponse = ContaResponseResumidoDTO.builder()
        .id(contaGeral.getId())
        .nome(contaGeral.getNome())
        .banco(contaGeral.getBanco())
        .tipoConta(contaGeral.getTipoConta())
        .build();

    UsuarioResponseResumidoDTO usuarioResponse = UsuarioResponseResumidoDTO.builder()
        .id(usuario.getId())
        .nome(usuario.getNome())
        .email(usuario.getEmail())
        .telefone(usuario.getTelefone())
        .build();

    if(categoria !=null){
       categoriaResponse = CategoriaResponseResumidoDTO.builder()
          .id(categoria.getId())
          .nome(categoria.getNome())
          .descricao(categoria.getDescricao())
          .build();
    }
    if(cartao !=null){
       cartaoResponse = CartaoResponseResumidoDTO.builder()
          .id(cartao.getId())
          .nome(cartao.getNome())
          .dataVencimento(cartao.getDataVencimento())
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
