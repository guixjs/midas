package com.residencia.backend.modules.controllers.conta;


import com.residencia.backend.modules.dto.conta.ContaDTO;
import com.residencia.backend.modules.services.conta.CalcularSaldoTotalService;
import com.residencia.backend.modules.services.conta.CriarContaService;
import com.residencia.backend.modules.services.conta.ExcluirContaService;
import com.residencia.backend.modules.services.conta.ListarContasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/account")
@Tag(name = "Contas",description = "Endpoints para gerenciar contas bancárias")
public class ContaController {

  @Autowired
  private CriarContaService criarContaService;

  @Autowired
    private ListarContasService listarContasService;

    @Autowired
    private ExcluirContaService excluirContaService;

    @Autowired
    private CalcularSaldoTotalService calcularSaldoTotalService;

@Operation(
    summary = "Criar conta bancária",
    method = "POST",
    description = "Cria uma nova conta bancária no sistema",
    responses = {
        @ApiResponse(responseCode = "200", description = "Conta criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody ContaDTO contaDTO, HttpServletRequest request){
    try{
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());
      var resultado = criarContaService.execute(contaDTO,idUser);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Operation(
      summary = "Listar contas",
      method = "GET",
      description = "Lista todas as contas do usuário autenticado",
      responses = {
          @ApiResponse(responseCode = "200", description = "Contas listadas com sucesso"),
          @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      }
  )
  @GetMapping
    public ResponseEntity<Object> list(HttpServletRequest request) {
        try {
            var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
            var resultado = this.listarContasService.execute(idUsuario);
            return ResponseEntity.ok().body(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

  @Operation(
      summary = "Deletar conta bancária",
      method = "DELETE",
      description = "Deleta uma conta bancária especificada pelo id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Conta deletada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Conta não encontrada"),
          @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id, HttpServletRequest request) {
        try {
            var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
            this.excluirContaService.execute(id, idUsuario);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("/saldo-total")
//    public ResponseEntity<Object> getSaldoTotal(HttpServletRequest request) {
//        try {
//            var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
//            var resultado = this.calcularSaldoTotalService.execute(idUsuario);
//            return ResponseEntity.ok().body(resultado);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}
