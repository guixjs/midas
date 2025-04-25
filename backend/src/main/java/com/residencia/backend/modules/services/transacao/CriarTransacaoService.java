package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.mapper.*;
import com.residencia.backend.modules.models.*;
import com.residencia.backend.modules.repositories.*;
import com.residencia.backend.modules.validator.TransacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    UsuarioEntity usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    CategoriaEntity categoria = transacaoValidator.validarCategoria(transacaoDTO.getIdCategoria(), idUsuario);
    Integer idConta = transacaoValidator.validarConta(transacaoDTO.getIdConta(), idUsuario);
    ContaEntity contaGeral = transacaoValidator.getContaById(idConta);
    CartaoEntity cartao = transacaoValidator.validarCartao(transacaoDTO.getIdCartao(), idConta, idUsuario);


    TransacaoEntity transacao = TransacaoMapper.toEntity(transacaoDTO, categoria, idConta,cartao, idUsuario);

    TransacaoEntity resultado = execute(transacao);

    ContaResponseResumidoDTO contaResponse = ContaMapper.toResponseResumidoDTO(contaGeral);
    UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(usuario);
    CartaoResponseResumidoDTO cartaoResponse = CartaoMapper.toResponseResumidoDTO(cartao);
    CategoriaResponseResumidoDTO categoriaResponse = CategoriaMapper.toResponseResumidoDTO(categoria);
    return TransacaoMapper.toResponseDTO(resultado,categoriaResponse,cartaoResponse,usuarioResponse,contaResponse);

  }

  public TransacaoResponseDTO montarResponseDTO(TransacaoEntity transacao) {
      CartaoResponseResumidoDTO cartaoResponse = null;
      CategoriaResponseResumidoDTO categoriaResponse = null;
      
      if(transacao.getCategoria() != null) {
          categoriaResponse = CategoriaResponseResumidoDTO.builder()
              .id(transacao.getCategoria().getId())
              .nome(transacao.getCategoria().getNome())
              .descricao(transacao.getCategoria().getDescricao())
              .build();
      }
      
      if(transacao.getCartao() != null) {
          cartaoResponse = CartaoResponseResumidoDTO.builder()
              .id(transacao.getCartao().getId())
              .nome(transacao.getCartao().getNome())
              .dataVencimento(transacao.getCartao().getDataVencimento())
              .build();
      }
      
      ContaResponseResumidoDTO contaResponse = null;
      if(transacao.getConta() != null) {
          contaResponse = ContaResponseResumidoDTO.builder()
              .idConta(transacao.getConta().getId()) //mudei aqui
              .nome(transacao.getConta().getNome())
              .banco(transacao.getConta().getBanco())
              .tipoConta(transacao.getConta().getTipoConta())
              .build();
      }
      
      UsuarioResponseResumidoDTO usuarioResponse = null;
      if(transacao.getUsuario() != null) {
          usuarioResponse = UsuarioResponseResumidoDTO.builder()
              .id(transacao.getUsuario().getId())
              .nome(transacao.getUsuario().getNome())
              .email(transacao.getUsuario().getEmail())
              .telefone(transacao.getUsuario().getTelefone())
              .build();
      }
      
      return TransacaoResponseDTO.builder()
          .id(transacao.getId())
          .descricao(transacao.getDescricao())
          .dataTransacao(transacao.getDataTransacao())
          .valor(transacao.getValor())
          .tipoTransacao(transacao.getTipoTransacao())
          .conta(contaResponse)
          .cartao(cartaoResponse)
          .usuario(usuarioResponse)
          .categoria(categoriaResponse)
          .build();
  }
}
