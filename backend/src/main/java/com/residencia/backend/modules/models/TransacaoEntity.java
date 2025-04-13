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

  private LocalDate data_transacao;

  @NotNull(message = "O valor não pode ser nulo")
  private BigDecimal valor;

  @Enumerated(EnumType.STRING)
  private TipoTransacao tipoTransacao;

  @ManyToOne
  @JoinColumn(name = "id_categoria", referencedColumnName = "id")
  private CategoriaEntity categoria;

//  @Column(name = "id_categoria")
//  private Integer id_categoria;

  @ManyToOne()
  @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
  private UsuarioEntity usuarioEntity;

  @Column(name = "id_usuario",nullable = false)
  private UUID idUsuario;


  @CreationTimestamp
  private LocalDateTime dt_criacao;


}
