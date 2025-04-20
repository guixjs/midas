package com.residencia.backend.modules.dto.cartao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoResponseDTO {
  private UUID id;
  private String nome;
  private LocalDate dataVencimento;
  private Integer idConta;
  private UUID idUsuario;
  private LocalDateTime dataCriacao;
}
