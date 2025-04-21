package com.residencia.backend.modules.models;


import com.residencia.backend.modules.enums.TipoTransacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transacoes")
public class TransacaoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String descricao;

  private LocalDate dataTransacao;

  @NotNull(message = "O valor não pode ser nulo")
  private BigDecimal valor;

  @Enumerated(EnumType.STRING)
  private TipoTransacao tipoTransacao;

  @ManyToOne
  @JoinColumn(name = "id_categoria", referencedColumnName = "id")
  private CategoriaEntity categoria;

  @ManyToOne()
  @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
  private UsuarioEntity usuarioEntity;

  @Column(name = "id_usuario",nullable = false)
  private UUID idUsuario;

  @ManyToOne()
  @JoinColumn(name = "id_conta",insertable = false,updatable = false)
  private ContaEntity contaEntity;

  @Column(name = "id_conta")
  private Integer idConta;

  @ManyToOne()
  @JoinColumn(name = "id_cartao",insertable = false,updatable = false)
  private CartaoEntity cartaoEntity;

  @Column(name = "id_cartao")
  private UUID idCartao;


  @CreationTimestamp
  @Column(name = "dt_criacao", updatable = false)
  private LocalDateTime dataCriacao;
}
