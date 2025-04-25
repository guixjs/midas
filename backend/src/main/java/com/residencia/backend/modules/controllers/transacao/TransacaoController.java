package com.residencia.backend.modules.controllers.transacao;

import java.util.List;
import com.residencia.backend.modules.dto.transacao.TransacaoPesquisaDTO;
import com.residencia.backend.modules.services.transacao.PesquisarTransacoesService;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.services.transacao.CriarTransacaoService;
import com.residencia.backend.modules.services.transacao.CsvImporterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransacaoController {

  @Autowired
  private CriarTransacaoService criarTransacaoService;

  @Autowired
  private PesquisarTransacoesService pesquisarTransacoesService;

  @Autowired
  private CsvImporterService csvImporterService;


  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody TransacaoDTO transacaoDTO, HttpServletRequest request){
    try{
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());
      TransacaoResponseDTO response = criarTransacaoService.criarTransacao(transacaoDTO, idUser);
      return ResponseEntity.ok().body(response);
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

  @PostMapping("/pesquisar")
    public ResponseEntity<List<TransacaoResponseDTO>> pesquisarTransacoes(
            @RequestBody TransacaoPesquisaDTO filtros,
            HttpServletRequest request) {
        
        UUID idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
        List<TransacaoResponseDTO> transacoes = pesquisarTransacoesService.execute(filtros, idUsuario);
        
        return ResponseEntity.ok(transacoes);
    }
}

