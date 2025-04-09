package com.residencia.backend.modules.controllers;

import com.residencia.backend.modules.models.UsuarioEntity;
import com.residencia.backend.modules.services.usuario.CriarUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsuarioController {

  @Autowired
  private CriarUsuarioService criarUsuarioService;

  @PostMapping("/criar")
  public ResponseEntity<Object> create(@Valid @RequestBody UsuarioEntity usuario){
    try{
      var resultado = this.criarUsuarioService.execute(usuario);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
