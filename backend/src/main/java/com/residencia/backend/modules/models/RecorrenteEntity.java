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
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transacoes_recorrentes")
public class RecorrenteEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String descricao;

  private BigDecimal valor;

  @Enumerated(EnumType.STRING)
  @NotNull(message = "O tipo não pode ser nulo")
  private TipoTransacao tipoTransacao;

  @Column(name = "repetir_valor", nullable = false)
  private boolean repetirValor;

  @ManyToOne
  @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
  private CategoriaEntity categoria;

  @Column(name = "id_categoria")
  private Integer idCategoria;

  @ManyToOne()
  @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
  private UsuarioEntity usuario;

  @Column(name = "id_usuario",nullable = false)
  private UUID idUsuario;

  @ManyToOne()
  @JoinColumn(name = "id_conta",insertable = false,updatable = false)
  private ContaEntity conta;

  @Column(name = "id_conta")
  private Integer idConta;

  @ManyToOne()
  @JoinColumn(name = "id_cartao",insertable = false,updatable = false)
  private CartaoEntity cartao;

  @Column(name = "id_cartao")
  private UUID idCartao;


  @CreationTimestamp
  @Column(name = "dt_criacao", updatable = false)
  private LocalDateTime dataCriacao;
}



