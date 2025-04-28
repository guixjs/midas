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
}
