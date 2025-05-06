package com.residencia.backend.modules.controllers.usuario;

import com.residencia.backend.modules.dto.usuario.UsuarioDTO;
import com.residencia.backend.modules.services.usuario.CriarUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuário", description = "Endpoint responsável pela criação de usuários")
public class UsuarioController {

  @Autowired
  private CriarUsuarioService criarUsuarioService;

  @Operation(
      summary = "Criar usuário",
      method = "POST",
      description = "Cria um novo usuário no sistema",
      responses = {
          @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody UsuarioDTO usuarioDTO){
    try{
      var resultado = this.criarUsuarioService.execute(usuarioDTO);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
