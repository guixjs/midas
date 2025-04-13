package com.residencia.backend.modules.controllers.categoria;

import com.residencia.backend.modules.dto.categoria.CategoriaDTO;
import com.residencia.backend.modules.models.CategoriaEntity;
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
@RequestMapping("/categoria")
public class CategoriaController {

  @Autowired
  private CriarCategoriaService criarCategoriaService;

  @PostMapping("/new")
  public ResponseEntity<Object> create(@Valid @RequestBody CategoriaDTO categoriaDTO, HttpServletRequest request){
    try{
      var idUser = request.getAttribute("id_usuario");
      var categoria = CategoriaEntity.builder()
          .descricao(categoriaDTO.getDescricao())
          .nome(categoriaDTO.getNome())
          .idUsuario(UUID.fromString(idUser.toString()))
          .build();
      var resultado = this.criarCategoriaService.execute(categoria);
      return ResponseEntity.ok().body(resultado);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
