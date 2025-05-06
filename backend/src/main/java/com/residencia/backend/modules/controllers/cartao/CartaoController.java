package com.residencia.backend.modules.controllers.cartao;

import com.residencia.backend.modules.dto.cartao.CartaoDTO;
import com.residencia.backend.modules.services.cartao.CriarCartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/card")
@Tag(name = "Cartões", description = "Endpoints para gerenciar cartões.")
public class CartaoController {

  @Autowired
  private CriarCartaoService criarCartaoService;


  @Operation(
      summary = "Cadastrar um novo cartão",
      method = "POST",
      description = "Cria um novo cartão no sistema",
      responses = {
          @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody CartaoDTO cartaoDTO, HttpServletRequest request){
    try{
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());
      var resultado = criarCartaoService.execute(cartaoDTO, idUser);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
