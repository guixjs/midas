package com.residencia.backend.modules.controllers.dashboard;


import com.residencia.backend.modules.dto.dashboard.DashboardDTO;
import com.residencia.backend.modules.services.dashboard.MontarDashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard",description = "Endpoint da tela inicial, que pode possuir parâmetros para tornar flexível")
public class DashboardController {

  @Autowired
  private MontarDashboardService montarDashboardService;

//  @GetMapping("/")
//  public ResponseEntity<Object> dashboard(
//      HttpServletRequest request,
//      @RequestParam(required = false) Integer idConta,
//      @RequestParam(required = false) TipoTransacao tipo,
//      @RequestParam(required = false) CriterioMeses criterioMeses,
//      @RequestParam(required = false) CriterioTransacoes criterioTransacoes) {
//    var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
//    return ResponseEntity.ok().build();
//  }
  @GetMapping
  public ResponseEntity<Object> dashboard(
      HttpServletRequest request,
      @RequestParam(required = false) Integer idConta

  ){
    try {
      var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
      DashboardDTO dashboardDTO =  montarDashboardService.execute(idUsuario,idConta);
      return ResponseEntity.ok().body(dashboardDTO);
    }catch (Exception e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
