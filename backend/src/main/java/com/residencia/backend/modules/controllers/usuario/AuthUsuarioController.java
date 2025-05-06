package com.residencia.backend.modules.controllers.usuario;

import com.residencia.backend.modules.dto.usuario.AuthUsuarioDTO;
import com.residencia.backend.modules.services.usuario.AutenticarUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "Autenticação", description = "Endpoint responsável pela autenticação de usuários")
public class AuthUsuarioController {

  @Autowired
  private AutenticarUsuarioService autenticarUsuarioService;
  @Operation(
      summary = "Autenticar usuário",
      method = "POST",
      description = "Autentica um usuário cadastrado no sistema, retornando seu token",
      responses = {
          @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
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
