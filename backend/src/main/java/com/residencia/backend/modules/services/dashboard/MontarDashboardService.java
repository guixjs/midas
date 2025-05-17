package com.residencia.backend.modules.services.dashboard;


import com.residencia.backend.modules.dto.dashboard.CategoriaMaiorGastoDTO;
import com.residencia.backend.modules.dto.dashboard.ContaInfoDTO;
import com.residencia.backend.modules.dto.dashboard.DashboardDTO;
import com.residencia.backend.modules.dto.dashboard.ResumoMensalDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.enums.TopTransacoes;
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
  @Autowired
  private TopTransacoesService topTransacoesService;
  @Autowired
  private ResumoMensalService resumoMensalService;

  public DashboardDTO execute(UUID idUsuario, Integer idConta, TopTransacoes topCriterio,YearMonth mesEAno, Integer qtdMeses) {
    if(mesEAno==null){
      mesEAno = YearMonth.now();
    }

    UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(()-> new OperacaoNaoPermitidaException("Usuário não encontrado"));


    UsuarioResponseResumidoDTO usuarioResponse = UsuarioMapper.toResponseResumidoDTO(usuario);
    ContaInfoDTO contaInfo = contaInfoService.montarContaInfo(idUsuario,idConta,mesEAno);
    List<CategoriaMaiorGastoDTO> categorias = categoriaMaiorGastoService.montarCategoriasMaisGastas(idUsuario,mesEAno);
    List<TransacaoResponseResumidoDTO> topTransacoes = topTransacoesService.montarListaTransacoesDashboard(idUsuario,topCriterio, mesEAno);
    List<ResumoMensalDTO> listaResumoMensal = resumoMensalService.montarListaResumo(idUsuario,qtdMeses);

    return DashboardDTO.builder()
        .usuarioInfo(usuarioResponse)
        .contaInfo(contaInfo)
        .categoriasMaisGastas(categorias)
        .topTransacoes(topTransacoes)
        .listaResumoMeses(listaResumoMensal)
        .build();
  }
}
