package com.residencia.backend.modules.controllers.transacao;


import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.services.transacao.CriarTransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransacaoController {

  @Autowired
  private CriarTransacaoService criarTransacaoService;

  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody TransacaoEntity transacao){
    try{
      var resultado = this.criarTransacaoService.execute(transacao);
      return ResponseEntity.ok().body(resultado);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
