package com.residencia.backend.modules.controllers.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaDTO;
import com.residencia.backend.modules.services.categoria.CriarCategoriaService;
import com.residencia.backend.modules.services.categoria.EditarCategoriaService;
import com.residencia.backend.modules.services.categoria.ExcluirCategoriaService;
import com.residencia.backend.modules.services.categoria.IdentificarCategoriaMaiorGastoService;
import com.residencia.backend.modules.services.categoria.ListarCategoriasService;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

@RestController
@RequestMapping("/category")
@Tag(name = "Categorias", description = "Endpoints para gerenciar categorias que classificam transações")
public class CategoriaController {

  @Autowired
  private CriarCategoriaService criarCategoriaService;

  @Autowired
  private EditarCategoriaService editarCategoriaService;

  @Autowired
  private ExcluirCategoriaService excluirCategoriaService;

  @Autowired
  private ListarCategoriasService listarCategoriasService;

  @Autowired
  private IdentificarCategoriaMaiorGastoService identificarCategoriaMaiorGastoService;

  @Operation(
          summary = "Criar nova categoria",
          method = "POST",
          description = "Cria uma nova categoria pertencente ao usuário",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso"),
                  @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                  @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
                  @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
          })
  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody CategoriaDTO categoriaDTO, HttpServletRequest request){
    try{
      var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
      var resultado = this.criarCategoriaService.execute(categoriaDTO,idUsuario);
      return ResponseEntity.ok().body(resultado);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Operation(
          summary = "Alterar categoria",
          method = "PUT",
          description = "Altera informações de uma categoria específica",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso"),
                  @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                  @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
                  @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
          })
  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO categoriaDTO, HttpServletRequest request) {
    try {
      var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
      var resultado = this.editarCategoriaService.execute(id, categoriaDTO, idUsuario);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Operation(
          summary = "Deletar categoria",
          method = "DELETE",
          description = "Apaga uma categoria específica pertencente ao usuário",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Categoria apagada com sucesso"),
                  @ApiResponse(responseCode = "400", description = "Categoria não encontrada"),
                  @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
                  @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
          })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable Integer id, HttpServletRequest request) {
    try {
      var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
      this.excluirCategoriaService.execute(id, idUsuario);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Operation(
          summary = "Listar categorias",
          method = "GET",
          description = "Lista todas as categorias, do sistema e do usuário",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Categoria encontradas com sucesso"),
                  @ApiResponse(responseCode = "401", description = "Não autorizado para realizar o serviço"),
                  @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
          })
  @GetMapping
  public ResponseEntity<Object> list(HttpServletRequest request) {
    try {
      var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
      var resultado = this.listarCategoriasService.execute(idUsuario);
      return ResponseEntity.ok().body(resultado);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
//  @Operation(
//    summary = "Identificar categoria com maior gasto",
//    description = "Retorna a categoria que possui o maior gasto total",
//    responses = {
//        @ApiResponse(responseCode = "200", description = "Categoria identificada com sucesso"),
//        @ApiResponse(responseCode = "401", description = "Não autorizado"),
//        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
//    }
//)
//@GetMapping("/maior-gasto")
//public ResponseEntity<Object> identificarCategoriaMaiorGasto(HttpServletRequest request) {
//    try {
//        var idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
//        var resultado = identificarCategoriaMaiorGastoService.execute(idUsuario);
//        return ResponseEntity.ok().body(resultado);
//    } catch (Exception e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
//}
}
