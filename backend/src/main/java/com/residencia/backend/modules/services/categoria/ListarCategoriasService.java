package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.mapper.CategoriaMapper;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListarCategoriasService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaResponseResumidoDTO> execute(UUID idUsuario) {
        return categoriaRepository.findCategoriasDisponiveis(idUsuario)
            .stream()
            .map(CategoriaMapper::toResponseResumidoDTO)
            .collect(Collectors.toList());

    }
}
