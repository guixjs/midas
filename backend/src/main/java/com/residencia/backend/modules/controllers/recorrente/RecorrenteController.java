package com.residencia.backend.modules.controllers.recorrente;

import com.residencia.backend.modules.dto.recorrente.RecorrenteDTO;
import com.residencia.backend.modules.dto.recorrente.RecorrenteResponseDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.services.recorrente.CriarRecorrenteService;
import com.residencia.backend.modules.services.recorrente.ListarRecorrentesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recurring")
@Tag(name = "Recorrentes",description = "Endpoints para gerenciar transações recorrentes")
public class RecorrenteController {

  @Autowired
  private CriarRecorrenteService criarRecorrenteService;
  @Autowired
  private ListarRecorrentesService listarRecorrentesService;

  @Operation(
      summary = "Cadastrar uma nova transação recorrente",
      method = "POST",
      description = "Cria uma nova transação recorrente no sistema",
      responses = {
          @ApiResponse(responseCode = "201", description = "Transação recorrente criada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody RecorrenteDTO recorrenteDTO, HttpServletRequest request){
    try {
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());
      RecorrenteResponseDTO response = criarRecorrenteService.criarRecorrente(recorrenteDTO, idUser);
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<Object> list(HttpServletRequest request){
    try {
      var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
      List<TransacaoDTO> resultado = this.listarRecorrentesService.execute(idUsuario);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
