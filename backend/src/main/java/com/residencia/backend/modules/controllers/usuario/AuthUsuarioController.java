package com.residencia.backend.modules.controllers.usuario;

import com.residencia.backend.modules.dto.usuario.AuthUsuarioDTO;
import com.residencia.backend.modules.services.usuario.AutenticarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthUsuarioController {

  @Autowired
  private AutenticarUsuarioService autenticarUsuarioService;

  @PostMapping("/auth")
  public ResponseEntity<Object> auth(@RequestBody AuthUsuarioDTO authUsuarioDTO){
    try {
      var resultado = this.autenticarUsuarioService.execute(authUsuarioDTO);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
