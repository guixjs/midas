package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.mapper.CategoriaMapper;
import com.residencia.backend.modules.models.CategoriaEntity;
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
        // Busca todas as categorias disponíveis para o usuário, incluindo as do sistema
        List<CategoriaEntity> categorias = categoriaRepository.findCategoriasDisponiveis(idUsuario);
        
        // Converte todas as categorias para DTO de resposta resumido
        
        return categorias.stream()
            .map(categoria -> CategoriaResponseResumidoDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .descricao(categoria.getDescricao())
                .build())
            .collect(Collectors.toList());
    }
}
