package com.residencia.backend.modules.dto.conta;

import com.residencia.backend.modules.enums.TipoConta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "DTO de criação de contas")
public class ContaDTO {
  @Schema(description = "Nome da conta", example = "Conta Principal")
  private String nome;
  @Schema(description = "Tipo da conta", example = "CORRENTE")
  private TipoConta tipoConta;
  @Schema(description = "Saldo inicial da conta", example = "4000")
  private BigDecimal saldo;
  @Schema(description = "Nome do banco à qual a conta pertence", example = "Banco do brasil")
  private String banco;
  @Schema(description = "Cor que representa a conta (em Hexadecimal)",example = "#FF5733")
  private String cor;
}
