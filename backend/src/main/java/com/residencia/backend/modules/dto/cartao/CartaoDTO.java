package com.residencia.backend.modules.dto.cartao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoDTO {
  private String nome;

  @NotNull(message = "Data de vencimento é obrigatória")
  @JsonFormat(pattern = "dd/MM/yyyy") // Usar JsonFormat para a desserialização
  private LocalDate dataVencimento;

  private Integer idConta;
}
