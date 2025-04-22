package com.residencia.backend.modules.dto.cartao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoResponseResumidoDTO {
  private String nome;
  private LocalDate dataVencimento;
}
