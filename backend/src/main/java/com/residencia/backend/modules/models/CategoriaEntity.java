package com.residencia.backend.modules.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity(name = "categoria")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "A categoria deve ter um nome")
  @Pattern(regexp = "^.{1,50}$",message = "O nome deve ter no máximo 50 caractéres")
  private String nome;

  private String descricao;

  @ManyToOne()
  @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
  private UsuarioEntity usuarioEntity;

  @Column(name = "id_usuario")
  private UUID id_usuario;

  testeee123huifuisdhjhsdajhsdajsdksdjdhsdd
}
