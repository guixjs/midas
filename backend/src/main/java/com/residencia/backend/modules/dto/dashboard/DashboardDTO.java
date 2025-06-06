package com.residencia.backend.modules.dto.dashboard;

import com.residencia.backend.modules.dto.transacao.TransacaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
  private UsuarioResponseResumidoDTO usuarioInfo;
  private ContaInfoDTO contaInfo;
  private List<CategoriaMaiorGastoDTO> categoriasMaisGastas;
  private List<TransacaoResponseResumidoDTO> topTransacoes;
  private List<ResumoMensalDTO> listaResumoMeses;

}
