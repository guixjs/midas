package com.residencia.backend.modules.dto.cartao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de cartões")
public class CartaoDTO {
  @Schema(description = "Nome dado ao cartão para diferenciá-lo",example = "Cartão black")
  private String nome;

  @NotNull(message = "Data de vencimento é obrigatória")
  @JsonFormat(pattern = "dd/MM/yyyy")
  @Schema(description = "Data de vencimento do cartão",example = "2025-04-10")
  private LocalDate dataVencimento;
  @Schema(description = "Cor que representa o cartão (em Hexadecimal)", example ="#171616")
  private String cor;
  @Schema(description = "Id da conta à qual o cartão pertence (É PRECISO CADASTRAR A CONTA ANTES)",example = "2")
  private Integer idConta;
}
