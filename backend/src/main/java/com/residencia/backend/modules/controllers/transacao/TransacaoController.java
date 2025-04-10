package com.residencia.backend.modules.controllers.transacao;


import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.services.transacao.CriarTransacaoService;
import com.residencia.backend.modules.services.transacao.CsvImporterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransacaoController {

  @Autowired
  private CriarTransacaoService criarTransacaoService;

  @Autowired
  private CsvImporterService csvImporterService;

  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody TransacaoDTO transacaoDTO, HttpServletRequest request){
    try{
      var idUser = request.getAttribute("id_usuario");

      BigDecimal valor = transacaoDTO.getValor();

      if(transacaoDTO.getTipoTransacao() == TipoTransacao.DEBITO){
        valor = valor.negate();
      }

      var transacao = TransacaoEntity.builder()
          .descricao(transacaoDTO.getDescricao())
          .data_transacao(transacaoDTO.getData_transacao())
          .valor(valor)
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
  @PostMapping("/import")
  public ResponseEntity<Object> importarCSV(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
    try {
      csvImporterService.importarCSV(file, request);
      return ResponseEntity.ok().body("Arquivo CSV importado com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Erro ao importar CSV: " + e.getMessage());
    }
  }
}

