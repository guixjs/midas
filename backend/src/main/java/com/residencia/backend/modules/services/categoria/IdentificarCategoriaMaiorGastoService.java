package com.residencia.backend.modules.services.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaMaiorGastoDTO;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdentificarCategoriaMaiorGastoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public CategoriaMaiorGastoDTO execute(UUID idUsuario) {
        return transacaoRepository.findCategoriaMaiorGasto(idUsuario);
    }
}
