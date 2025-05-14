package com.residencia.backend.modules.controllers.recorrente;

import com.residencia.backend.modules.dto.recorrente.RecorrenteDTO;
import com.residencia.backend.modules.dto.recorrente.RecorrenteResponseDTO;
import com.residencia.backend.modules.dto.recorrente.RecorrenteResponseResumidoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseResumidoDTO;
import com.residencia.backend.modules.mapper.TransacaoMapper;
import com.residencia.backend.modules.services.recorrente.*;
import com.residencia.backend.modules.services.transacao.CriarTransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
  @Autowired
  private CriarTransacaoService criarTransacaoService;
  @Autowired
  private ExcluirRecorrenteService excluirRecorrenteService;
  @Autowired
  private EditarRecorrenteService editarRecorrenteService;
  @Autowired
  private ListarRecorrenteEspecificaService listarRecorrenteEspecificaService;

  @Operation(
      summary = "Cadastrar uma nova transação recorrente",
      method = "POST",
      description = "Cria uma nova transação recorrente no sistema, usada para automatizar o cadastro de transações que ocorrem com frequência.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Exemplo de payload para criar uma transação",
          required = true,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = {
                  @ExampleObject(
                      name = "Exemplo de recorrente SEM repetição de valor",
                      description = "Ao marcar a opção 'repetirValor' como falsa, o valor não precisa ser informado",
                      value = "{\n" +
                          "  \"descricao\": \"Conta de luz\",\n" +
                          "  \"valor\": 200.00,\n" +
                          "  \"tipoTransacao\": \"DESPESA\",\n" +
                          "  \"idCategoria\": 2,\n" +
                          "  \"idConta\": 1,\n" +
                          "  \"idCartao\": null,\n" +
                          "  \"repetirValor\": false\n" +
                          "}"
                  ),
                  @ExampleObject(
                      name = "Exemplo de recorrente COM repetição de valor",
                      description = "Ao marcar a opção 'repetirValor' como verdadeira, o valor precisa ser informado",
                      value = "{\n" +
                          "  \"descricao\": \"Aluguel\",\n" +
                          "  \"dataTransacao\": \"2025-08-28\",\n" +
                          "  \"valor\": 1500.00,\n" +
                          "  \"tipoTransacao\": \"DESPESA\",\n" +
                          "  \"idCategoria\": 2,\n" +
                          "  \"idConta\": 1,\n" +
                          "  \"idCartao\": null,\n" +
                          "  \"repetirValor\": true\n" +
                          "}"
                  )
              }
          )
      ),
      responses = {
          @ApiResponse(responseCode = "201", description = "Transação recorrente criada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      }
  )
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

  @Operation(
      summary = "Converte as recorrentes em transações",
      method = "POST",
      description = "Usando o retorno da rota GET/recurring, converte e persiste a lista de recorrentes em transações",
      responses = {
          @ApiResponse(responseCode = "200", description = "Transações recorrentes convertidas com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @PostMapping("/many")
  public ResponseEntity<Object> save(@Valid @RequestBody List<TransacaoDTO> transacoes, HttpServletRequest request){
    try {
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());

      List<TransacaoResponseResumidoDTO> response = new ArrayList<>();

      for (TransacaoDTO transacao : transacoes) {
        TransacaoResponseDTO salva = criarTransacaoService.criarTransacao(transacao, idUser);
        TransacaoResponseResumidoDTO resumido = TransacaoMapper.toResponseResumidoConversaoRecorrente(salva);
        response.add(resumido);
      }
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @Operation(
      summary = "Lista as transações recorrentes",
      method = "GET",
      description = "Lista todas as transações recorrentes cadastradas",
      responses = {
          @ApiResponse(responseCode = "200", description = "Transações recorrentes listadas com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
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
  @Operation(
      summary = "Apagar uma transação recorrente específica",
      method = "DELETE",
      description = "Apaga a transação recorrente especificada pelo id.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Transação recorrente deletada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Transação recorrente não encontrada"),
          @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable UUID id, HttpServletRequest request){
    try {
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());
      this.excluirRecorrenteService.execute(id, idUser);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @Operation(
      summary = "Atualizar uma transação recorrente específica",
      method = "PUT",
      description = "Altera os dados de uma transação recorrente, passando os novos dados pelo body.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Transação recorrente alterada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody RecorrenteDTO recorrenteDTO, HttpServletRequest request){
    try {
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());
      RecorrenteResponseResumidoDTO response = editarRecorrenteService.execute(recorrenteDTO,id, idUser);
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @Operation(
      summary = "Listar uma transação recorrente específica",
      method = "GET",
      description = "Retorna a transação recorrente encontrada pelo id.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Transação recorrente encontrada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Transação recorrente não encontrada"),
          @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @GetMapping("/{id}")
  public ResponseEntity<Object> especifica(@PathVariable UUID id, HttpServletRequest request){
    try {
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());
      RecorrenteResponseDTO response = listarRecorrenteEspecificaService.execute(id, idUser);
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
