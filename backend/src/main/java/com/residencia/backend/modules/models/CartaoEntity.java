package com.residencia.backend.modules.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "cartao")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartaoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull(message = "Data de vencimento é obrigatória")
  private LocalDate dataVencimento;

  @NotBlank(message = "O cartão deve ter um nome")
  @Pattern(regexp = "^.{1,30}$", message = "O nome deve ter no máximo 30 caracteres")
  private String nome;

  @NotBlank(message = "Deve adicionar uma cor")
  @Column(length = 7)
  private String cor;

  @ManyToOne
  @JoinColumn(name = "id_conta", insertable = false, updatable = false)
  private ContaEntity conta;

  @Column(name = "id_conta", nullable = false)
  private Integer idConta;

  @ManyToOne
  @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
  private UsuarioEntity usuarioEntity;

  @Column(name = "id_usuario", nullable = false)
  private UUID idUsuario;

  @CreationTimestamp
  @Column(name = "dt_criacao", updatable = false)
  private LocalDateTime dataCriacao;
}
