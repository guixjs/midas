package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.mapper.CategoriaMapper;
import com.residencia.backend.modules.mapper.UsuarioMapper;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EditarCategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public CategoriaResponseDTO execute(Integer id, CategoriaDTO categoriaDTO, UUID idUsuario) {
        CategoriaEntity categoria = categoriaRepository.findByIdAndPossivelUsuario(id, idUsuario)
            .orElseThrow(() -> new OperacaoNaoPermitidaException("Categoria não encontrada ou sem permissão"));

        String nome = categoriaDTO.getNome().toLowerCase();
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);

        // Verifica se já existe outra categoria com o mesmo nome
        categoriaRepository.findByNomeAndIdUsuario(nome, idUsuario)
            .ifPresent(cat -> {
                if (!cat.getId().equals(id)) {
                    throw new OperacaoNaoPermitidaException("Já existe uma categoria com este nome");
                }
            });

        categoria.setNome(nome);
        categoria.setDescricao(categoriaDTO.getDescricao());

        CategoriaEntity resultado = categoriaRepository.save(categoria);
        
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(usuarioEntity);
        return CategoriaMapper.toResponseDTO(resultado, usuarioResponse);
    }
}
