package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExcluirCategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public void execute(Integer id, UUID idUsuario) {
        CategoriaEntity categoria = categoriaRepository.findByIdAndPossivelUsuario(id, idUsuario)
            .orElseThrow(() -> new OperacaoNaoPermitidaException("Categoria não encontrada ou sem permissão"));

        if (categoria.getIdUsuario() == null) {
            throw new OperacaoNaoPermitidaException("Categorias do sistema não podem ser excluídas");
        }

        categoriaRepository.delete(categoria);
    }
}
