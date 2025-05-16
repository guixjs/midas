package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.dto.dashboard.CategoriaMaiorGastoDTO;
import com.residencia.backend.modules.repositories.CategoriaRepository;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdentificarCategoriaMaiorGastoService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaMaiorGastoDTO execute(UUID idUsuario) {
        return categoriaRepository.findCategoriaMaiorGasto(idUsuario);
    }
}
