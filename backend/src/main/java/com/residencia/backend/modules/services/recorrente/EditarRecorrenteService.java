package com.residencia.backend.modules.services.recorrente;

import com.residencia.backend.modules.dto.recorrente.RecorrenteDTO;
import com.residencia.backend.modules.dto.recorrente.RecorrenteResponseResumidoDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.mapper.RecorrenteMapper;
import com.residencia.backend.modules.models.RecorrenteEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.RecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EditarRecorrenteService {

  @Autowired
  private RecorrenteRepository recorrenteRepository;
  @Autowired
  private CategoriaRepository categoriaRepository;

  public RecorrenteResponseResumidoDTO execute(RecorrenteDTO recorrenteDTO, UUID id, UUID idUser) {
    RecorrenteEntity recorrente = recorrenteRepository.findByIdAndIdUsuario(id,idUser)
        .orElseThrow(()-> new OperacaoNaoPermitidaException("Recorrente não encontrada"));

    if(recorrenteDTO.getValor() != null){
      recorrente.setValor(recorrenteDTO.getValor());
    }
    if(recorrenteDTO.getDescricao() != null){
      recorrente.setDescricao(recorrenteDTO.getDescricao());
    }

    if(recorrenteDTO.getIdCategoria() != null){
      var categoria = categoriaRepository.findById(recorrenteDTO.getIdCategoria())
          .orElseThrow(() -> new OperacaoNaoPermitidaException("Categoria não encontrada"));
      recorrente.setIdCategoria(categoria.getId());
    }
    if(recorrenteDTO.getTipoTransacao() != null){
      recorrente.setTipoTransacao(recorrenteDTO.getTipoTransacao());
    }

    var resultado = recorrenteRepository.save(recorrente);
    return RecorrenteMapper.toResponseResumidoDTO(resultado);
  }
}
