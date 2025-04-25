package com.residencia.backend.modules.controllers.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaDTO;
import com.residencia.backend.modules.services.categoria.CriarCategoriaService;
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
@RequestMapping("/category")
public class CategoriaController {

  @Autowired
  private CriarCategoriaService criarCategoriaService;

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
}
