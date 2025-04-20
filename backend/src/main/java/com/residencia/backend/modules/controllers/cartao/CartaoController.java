package com.residencia.backend.modules.controllers.cartao;

import com.residencia.backend.modules.dto.cartao.CartaoDTO;
import com.residencia.backend.modules.dto.conta.ContaDTO;
import com.residencia.backend.modules.exceptions.OperacaoNaoPermitidaException;
import com.residencia.backend.modules.models.CartaoEntity;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.services.cartao.CriarCartaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
      var cartao = CartaoEntity.builder()
          .nome(cartaoDTO.getNome())
          .dataVencimento(cartaoDTO.getDataVencimento())
          .idConta(cartaoDTO.getIdConta())
          .idUsuario(idUser)
          .build();
      var resultado = criarCartaoService.execute(cartao, idUser);
      return ResponseEntity.ok().body(resultado);
    } catch (OperacaoNaoPermitidaException e) {
      // Captura específica para esta exceção
      return ResponseEntity.badRequest().body(e.getMessage());

    } catch (Exception e) {
      // Captura genérica para outros erros
      return ResponseEntity.internalServerError().body("Ocorreu um erro inesperado");
    }
  }
}
