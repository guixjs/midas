package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.mapper.*;
import com.residencia.backend.modules.models.*;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListarTransacaoEspecificaService {

  @Autowired
  private TransacaoRepository transacaoRepository;

  public TransacaoResponseDTO execute(UUID id, UUID idUsuario) {
    TransacaoEntity transacao = transacaoRepository.findByIdAndIdUsuario(id, idUsuario).orElse(null);

    ContaResponseResumidoDTO contaResponse = ContaMapper.toResponseResumidoDTO(transacao.getConta());
    UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(transacao.getUsuario());
    CartaoResponseResumidoDTO cartaoResponse = CartaoMapper.toResponseResumidoDTO(transacao.getCartao());
    CategoriaResponseResumidoDTO categoriaResponse = CategoriaMapper.toResponseResumidoDTO(transacao.getCategoria());

    return TransacaoMapper.toResponseDTO(transacao,categoriaResponse,cartaoResponse,usuarioResponse,contaResponse);
  }
}
