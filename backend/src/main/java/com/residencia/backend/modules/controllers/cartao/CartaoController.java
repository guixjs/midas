package com.residencia.backend.modules.controllers.cartao;

import com.residencia.backend.modules.dto.cartao.CartaoDTO;
import com.residencia.backend.modules.services.cartao.CriarCartaoService;
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
public class CartaoController {

  @Autowired
  private CriarCartaoService criarCartaoService;
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
