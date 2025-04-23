package com.residencia.backend.modules.dto.cartao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoResponseResumidoDTO {
  private UUID id;
  private String nome;
  private LocalDate dataVencimento;
}
