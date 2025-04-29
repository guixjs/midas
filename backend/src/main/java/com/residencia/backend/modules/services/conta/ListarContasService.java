package com.residencia.backend.modules.services.conta;

import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.mapper.ContaMapper;
import com.residencia.backend.modules.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListarContasService {

    @Autowired
    private ContaRepository contaRepository;

    public List<ContaResponseResumidoDTO> execute(UUID idUsuario) {
        return contaRepository.findByIdUsuario(idUsuario)
            .stream()
            .map(ContaMapper::toResponseResumidoDTO)
            .collect(Collectors.toList());
    }
}