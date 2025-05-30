package com.residencia.backend.modules.models;

import com.residencia.backend.modules.enums.TipoTransacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

  @Enumerated(EnumType.STRING)
  @NotNull(message = "O tipo não pode ser nulo")
  private TipoTransacao tipoTransacao;

  @NotBlank(message = "Deve adicionar uma cor")
  @Column(length = 7)
  private String cor;

  @ManyToOne()
  @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
  private UsuarioEntity usuarioEntity;

  @Column(name = "id_usuario")
  private UUID idUsuario;

  @CreationTimestamp
  @Column(name = "dt_criacao", updatable = false)
  private LocalDateTime dataCriacao;

}
