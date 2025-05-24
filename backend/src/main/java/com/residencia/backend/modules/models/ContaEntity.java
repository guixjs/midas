package com.residencia.backend.modules.models;


import com.residencia.backend.modules.enums.TipoConta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "conta")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "A conta deve ter um nome")
  @Pattern(regexp = "^.{1,50}$",message = "O nome deve ter no máximo 50 caractéres")
  private String nome;

  @NotBlank(message = "Deve adicionar uma cor")
  @Column(length = 7)
  private String cor;

  @NotNull(message = "O saldo não deve ser nulo")
  private BigDecimal saldo;

  @Enumerated(EnumType.STRING)
  private TipoConta tipoConta;

  @ManyToOne()
  @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
  private UsuarioEntity usuarioEntity;

  @Column(name = "id_usuario",nullable = false)
  private UUID idUsuario;

  private String banco;

  @CreationTimestamp
  @Column(name = "dt_criacao", updatable = false)
  private LocalDateTime dataCriacao;

}
