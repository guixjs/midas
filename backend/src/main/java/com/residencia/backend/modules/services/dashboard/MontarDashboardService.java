package com.residencia.backend.modules.services.dashboard;


import com.residencia.backend.modules.dto.dashboard.CategoriaMaiorGastoDTO;
import com.residencia.backend.modules.dto.dashboard.ContaInfoDTO;
import com.residencia.backend.modules.dto.dashboard.DashboardDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.mapper.UsuarioMapper;
import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
public class MontarDashboardService {

  @Autowired
  private ContaInfoService contaInfoService;

  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private CategoriaMaiorGastoService categoriaMaiorGastoService;

  public DashboardDTO execute(UUID idUsuario, Integer idConta) {
    YearMonth mesEAno = YearMonth.now();

    UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(()-> new OperacaoNaoPermitidaException("Usuário não encontrado"));
    List<CategoriaMaiorGastoDTO> categorias = categoriaMaiorGastoService.montarCategoriasMaisGastas(idUsuario,mesEAno);


    UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(usuario);
    ContaInfoDTO contaInfo = contaInfoService.montarContaInfo(idUsuario,idConta);

    return DashboardDTO.builder()
        .usuarioInfo(usuarioResponse)
        .contaInfoDTO(contaInfo)
        .categoriasMaisGastas(categorias)
        .build();
  }
}
