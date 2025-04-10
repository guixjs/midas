package com.residencia.backend.modules.controllers.transacao;


import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.services.transacao.CriarTransacaoService;
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
@RequestMapping("/transaction")
public class TransacaoController {

  @Autowired
  private CriarTransacaoService criarTransacaoService;

  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody TransacaoDTO transacaoDTO, HttpServletRequest request){
    try{
      var idUser = request.getAttribute("id_usuario");

      var transacao = TransacaoEntity.builder()
          .descricao(transacaoDTO.getDescricao())
          .data_transacao(transacaoDTO.getData_transacao())
          .valor(transacaoDTO.getValor())
          .tipoTransacao(transacaoDTO.getTipoTransacao())
          .categoria(transacaoDTO.getCategoria())
          .id_usuario(UUID.fromString(idUser.toString()))
          .build();


      var resultado = this.criarTransacaoService.execute(transacao);
      return ResponseEntity.ok().body(resultado);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
