package com.residencia.backend.modules.services.cartao;

import com.residencia.backend.modules.dto.cartao.CartaoDTO;
import com.residencia.backend.modules.dto.cartao.CartaoResponseDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.mapper.CartaoMapper;
import com.residencia.backend.modules.mapper.ContaMapper;
import com.residencia.backend.modules.mapper.UsuarioMapper;
import com.residencia.backend.modules.models.CartaoEntity;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.CartaoRepository;
import com.residencia.backend.modules.repositories.ContaRepository;
import com.residencia.backend.modules.repositories.UsuarioRepository;
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
  private UsuarioRepository usuarioRepository;

  @Autowired
  private ContaRepository contaRepository;

  public CartaoResponseDTO execute(CartaoDTO cartaoDTO, UUID idUsuario){


    var conta = contaRepository.findByIdAndIdUsuario(cartaoDTO.getIdConta(), idUsuario)
        .orElseThrow(() -> new OperacaoNaoPermitidaException("Conta não encontrada"));

    if(conta.getNome().equals("Geral") ) {
      throw new OperacaoNaoPermitidaException("O cartão não pode ser vinculado à conta Geral");
    }

    boolean cartaoExistente = cartaoRepository.existsByIdUsuarioAndNome(idUsuario,cartaoDTO.getNome());
    if (cartaoExistente) {
      throw new RuntimeException("Você já possui um cartão com esse nome.");
    }

    UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    CartaoEntity cartao = CartaoMapper.toEntity(cartaoDTO,idUsuario,conta.getId());
    CartaoEntity resultado = cartaoRepository.save(cartao);


    ContaResponseResumidoDTO contaResponse = ContaMapper.toResponseResumidoDTO(conta);
    UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(usuarioEntity);
    return CartaoMapper.toResponseDTO(resultado,contaResponse,usuarioResponse);
  }
}
