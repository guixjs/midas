package com.residencia.backend.modules.controllers.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaDTO;
import com.residencia.backend.modules.services.categoria.CriarCategoriaService;
import com.residencia.backend.modules.services.categoria.EditarCategoriaService;
import com.residencia.backend.modules.services.categoria.ExcluirCategoriaService;
import com.residencia.backend.modules.services.categoria.ListarCategoriasService;

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
public class CategoriaController {

  @Autowired
  private CriarCategoriaService criarCategoriaService;

  @Autowired
    private EditarCategoriaService editarCategoriaService;

    @Autowired
    private ExcluirCategoriaService excluirCategoriaService;

    @Autowired
    private ListarCategoriasService listarCategoriasService;

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
}

