package com.residencia.backend.modules.controllers.transacao;

import java.util.List;

import com.residencia.backend.modules.dto.transacao.TransacaoPesquisaDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseResumidoDTO;
import com.residencia.backend.modules.services.transacao.*;
import com.residencia.backend.modules.dto.transacao.TransacaoDTO;
import com.residencia.backend.modules.dto.transacao.TransacaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transações", description = "Endpoints para gerenciar transações financeiras")
public class TransacaoController {

  @Autowired
  private CriarTransacaoService criarTransacaoService;

  @Autowired
  private PesquisarTransacoesService pesquisarTransacoesService;

  @Autowired
  private ListarTransacaoEspecificaService listarTransacaoEspecificaService;

  @Autowired
  private ExcluirTransacaoService excluirTransacaoService;

  @Autowired
  private EditarTransacaoService editarTransacaoService;

  @Autowired
  private CsvImporterService csvImporterService;

  @Autowired
  private ContarTransacoesMensaisService contarTransacoesMensaisService;

  @Autowired
  private ListarPrincipaisDespesasService listarPrincipaisDespesasService;


  @Operation(
      summary = "Cadastrar uma nova transação",
      method = "POST",
      description = "Cria uma nova transação no sistema (despesa ou receita).",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Exemplo de payload para criar uma transação",
          required = true,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(
                  name = "Exemplo de transação sem cartão",
                  value = "{\n" +
                      "  \"descricao\": \"Viagem de uber\",\n" +
                      "  \"dataTransacao\": \"2025-08-27\",\n" +
                      "  \"valor\": 15.00,\n" +
                      "  \"tipoTransacao\": \"DESPESA\",\n" +
                      "  \"idCategoria\": 2,\n" +
                      "  \"idConta\": 1,\n" +
                      "  \"idCartao\": null\n" +
                      "}"
              )
          )
      ),
      responses = {
          @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })

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

  @Operation(
      summary = "Importar extrato CSV",
      method = "POST",
      description = "Importa um extrato CSV cadastrando as transações presentes nele",
      responses = {
          @ApiResponse(responseCode = "200", description = "CSV importado com sucesso"),
          @ApiResponse(responseCode = "400", description = "CSV inválido"),
          @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @PostMapping(value = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Object> importarCSV(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
    try {
      csvImporterService.importarCSV(file, request);
      return ResponseEntity.ok().body("Arquivo CSV importado com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Erro ao importar CSV: " + e.getMessage());
    }
  }

  @Operation(
      summary = "Listar e filtrar transações",
      method = "POST",
      description = "Retorna todas as transações do usuário autenticado. Permite aplicar filtros opcionais, como tipo (receita ou despesa), categoria, datas, faixa de valor, entre outros.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = TransacaoPesquisaDTO.class),
              examples = {
                  @ExampleObject(
                      name = "com filtros",
                      summary = "Busca com múltiplos filtros aplicados",
                      value = "{\n" +
                          "  \"dataInicio\": \"2024-05-01\",\n" +
                          "  \"dataFim\": \"2024-05-31\",\n" +
                          "  \"idCategoria\": 2,\n" +
                          "  \"tipoTransacao\": \"DESPESA\",\n" +
                          "  \"possuiCartao\": true,\n" +
                          "  \"idConta\": 5,\n" +
                          "  \"valorMin\": 50.00,\n" +
                          "  \"valorMax\": 500.00\n" +
                          "}"
                  ),
                  @ExampleObject(
                      name = "sem filtros",
                      summary = "Busca sem aplicar nenhum filtro",
                      value = "{\n}"
                  )
              }
          )
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "Transações encontradas com sucesso"),
          @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      }
  )
  @PostMapping("/search")
    public ResponseEntity<List<TransacaoResponseResumidoDTO>> pesquisarTransacoes(
            @RequestBody TransacaoPesquisaDTO filtros,
            HttpServletRequest request) {
        
        UUID idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
        List<TransacaoResponseResumidoDTO> transacoes = pesquisarTransacoesService.execute(filtros, idUsuario);
        
        return ResponseEntity.ok(transacoes);
    }

  @Operation(
      summary = "Listar uma transação específica",
      method = "GET",
      description = "Retorna a transação encontrada pelo id.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Transação não encontrada"),
          @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @GetMapping("/{id}")
  public ResponseEntity<Object> list(@PathVariable UUID id,HttpServletRequest request) {
    try {
      var idUser = UUID.fromString(request.getAttribute("id_usuario").toString());
      TransacaoResponseDTO response = listarTransacaoEspecificaService.execute(id, idUser);
      return ResponseEntity.ok().body(response);
    }catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Operation(
      summary = "Apagar uma transação específica",
      method = "DELETE",
      description = "Apaga a transação especificada pelo id.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Transação deletada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Transação não encontrada"),
          @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable UUID id, HttpServletRequest request) {
    try {
      var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
      this.excluirTransacaoService.execute(id, idUsuario);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @Operation(
      summary = "Atualizar uma transação específica",
      method = "PUT",
      description = "Altera os dados de uma transação, passando os novos dados pelo body.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Transação alterada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos"),
          @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
          @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody TransacaoDTO transacaoDTO, HttpServletRequest request) {
    try {
      var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
      TransacaoResponseResumidoDTO resultado = this.editarTransacaoService.execute(id, transacaoDTO, idUsuario);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @Operation(
    summary = "Contar transações mensais",
    description = "Retorna o número de transações em um mês específico",
    responses = {
        @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    }
)
  @GetMapping("/count/{mes}/{ano}")
  public ResponseEntity<Object> contarTransacoesMensais(
      @PathVariable Integer mes,
      @PathVariable Integer ano,
      HttpServletRequest request
  ) {
      try {
          var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
          var resultado = contarTransacoesMensaisService.execute(idUsuario, mes, ano);
          return ResponseEntity.ok().body(resultado);
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }
  @Operation(
    summary = "Listar principais despesas",
    description = "Retorna as 10 maiores despesas do usuário",
    responses = {
        @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    }
)
  @GetMapping("/top-despesas")
  public ResponseEntity<Object> listarPrincipaisDespesas(HttpServletRequest request) {
      try {
          var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
          var resultado = listarPrincipaisDespesasService.execute(idUsuario);
          return ResponseEntity.ok().body(resultado);
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }
}

