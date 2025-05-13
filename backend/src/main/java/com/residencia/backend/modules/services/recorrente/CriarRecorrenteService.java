package com.residencia.backend.modules.services.recorrente;


import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.recorrente.RecorrenteDTO;
import com.residencia.backend.modules.dto.recorrente.RecorrenteResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.mapper.*;
import com.residencia.backend.modules.models.*;
import com.residencia.backend.modules.repositories.RecorrenteRepository;
import com.residencia.backend.modules.repositories.UsuarioRepository;
import com.residencia.backend.modules.validator.TransacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarRecorrenteService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private TransacaoValidator validator;

  @Autowired
  private RecorrenteRepository recorrenteRepository;

  public RecorrenteResponseDTO criarRecorrente(RecorrenteDTO recorrenteDTO, UUID idUsuario) {

    UsuarioEntity usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    CategoriaEntity categoria = validator.validarCategoria(recorrenteDTO.getIdCategoria(), idUsuario);
    Integer idConta = validator.validarConta(recorrenteDTO.getIdConta(), idUsuario);
    ContaEntity contaGeral = validator.getContaById(idConta);
    CartaoEntity cartao = validator.validarCartao(recorrenteDTO.getIdCartao(), idConta, idUsuario);

    RecorrenteEntity recorrente = RecorrenteMapper.toEntity(recorrenteDTO,categoria,idConta,cartao,idUsuario);

    RecorrenteEntity resultado = this.recorrenteRepository.save(recorrente);

    ContaResponseResumidoDTO contaResponse = ContaMapper.toResponseResumidoDTO(contaGeral);
    UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(usuario);
    CartaoResponseResumidoDTO cartaoResponse = CartaoMapper.toResponseResumidoDTO(cartao);
    CategoriaResponseResumidoDTO categoriaResponse = CategoriaMapper.toResponseResumidoDTO(categoria);

    return RecorrenteMapper.toResponseDTO(resultado,categoriaResponse,cartaoResponse,usuarioResponse,contaResponse);


  }
}
