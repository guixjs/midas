package com.residencia.backend.modules.controllers.dashboard;


import com.residencia.backend.modules.dto.dashboard.DashboardDTO;
import com.residencia.backend.modules.enums.TopTransacoes;
import com.residencia.backend.modules.services.dashboard.MontarDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard",description = "Endpoint da tela inicial, que pode possuir parâmetros para tornar flexível")
public class DashboardController {

  @Autowired
  private MontarDashboardService montarDashboardService;

  @Operation(
      summary = "Retorna informações do dashboard financeiro",
      description = "Retorna dados do dashboard como saldo geral, categorias que mais gastaram, top transações e uma lista de resumos mensais, podendo filtrar com base nos parâmetros passados na rota",
      responses = {
          @ApiResponse(responseCode = "200", description = "Dashboard retornado com sucesso"),
          @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
          @ApiResponse(responseCode = "500", description = "Erro ao retornar o dashboard")
      }
  )

  @GetMapping
  public ResponseEntity<Object> dashboard(
      HttpServletRequest request,
      @Parameter(
          in = ParameterIn.QUERY,
          description = "Id da conta (Opcional, para filtrar os dados de uma conta específica)",
          example = "2"
      )
      @RequestParam(required = false) Integer idConta,
      @Parameter(
          in = ParameterIn.QUERY,
          description = "Critério de seleção para as 10 transações destacadas no dashboard",
          example = "DESPESAS_MES",
          schema = @Schema(implementation = TopTransacoes.class)
      )
      @RequestParam(required = false) TopTransacoes top,
      @Parameter(
          in = ParameterIn.QUERY,
          description = "Ano e mês de referência para filtrar dados (formato yyyy-MM)",
          example = "2024-04"
      )
      @RequestParam(required = false) YearMonth yearMonth,
      @Parameter(
          in = ParameterIn.QUERY,
          description = "Quantidade de meses que a lista de resumos mensais retorna",
          example = "12"
      )
      @RequestParam(required = false) Integer meses,//quantidade de meses no gráfico
      @Parameter(
          in = ParameterIn.QUERY,
          description = "Quantidade de transações que a lista de top transações retorna (min=5 e max=10)",
          example = "1"
      )
      @RequestParam(required = false) Integer qtdTransacoes
  ){
    try {
      var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
      DashboardDTO dashboardDTO =  montarDashboardService.execute(idUsuario,idConta,top,yearMonth,meses,qtdTransacoes);
      return ResponseEntity.ok().body(dashboardDTO);
    }catch (Exception e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
