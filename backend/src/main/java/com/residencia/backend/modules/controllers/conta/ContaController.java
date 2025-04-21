package com.residencia.backend.modules.controllers.conta;


import com.residencia.backend.modules.dto.conta.ContaDTO;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.services.conta.CriarContaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class ContaController {

  @Autowired
  private CriarContaService criarContaService;

  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody ContaDTO contaDTO, HttpServletRequest request){
    try{
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());
      var conta = ContaEntity.builder()
          .nome(contaDTO.getNome())
          .banco(contaDTO.getBanco())
          .tipoConta(contaDTO.getTipoConta())
          .idUsuario(idUser)
          .build();
      var resultado = criarContaService.execute(conta,idUser);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
