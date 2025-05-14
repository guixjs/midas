package com.residencia.backend.modules.services.recorrente;

import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.recorrente.RecorrenteResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.mapper.*;
import com.residencia.backend.modules.models.RecorrenteEntity;
import com.residencia.backend.modules.repositories.RecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListarRecorrenteEspecificaService {

  @Autowired
  private RecorrenteRepository recorrenteRepository;
  public RecorrenteResponseDTO execute(UUID id, UUID idUsuario) {
    RecorrenteEntity recorrente = recorrenteRepository.findByIdAndIdUsuario(id, idUsuario).orElse(null);

    ContaResponseResumidoDTO contaResponse = ContaMapper.toResponseResumidoDTO(recorrente.getConta());
    UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(recorrente.getUsuario());
    CartaoResponseResumidoDTO cartaoResponse = CartaoMapper.toResponseResumidoDTO(recorrente.getCartao());
    CategoriaResponseResumidoDTO categoriaResponse = CategoriaMapper.toResponseResumidoDTO(recorrente.getCategoria());

    return RecorrenteMapper.toResponseDTO(recorrente,categoriaResponse,cartaoResponse,usuarioResponse,contaResponse);
  }
}
