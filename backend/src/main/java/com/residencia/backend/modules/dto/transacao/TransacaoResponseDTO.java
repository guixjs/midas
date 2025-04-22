package com.residencia.backend.modules.dto.transacao;

import com.residencia.backend.modules.dto.categoria.CategoriaResponseDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResponseDTO {
  private UUID id;
  private String descricao;
  private LocalDate dataTransacao;
  private BigDecimal valor;
  private TipoTransacao tipoTransacao;
  private CategoriaResponseDTO categoria;
  private Integer idConta;
  private UUID idCartao;
  private UUID idUsuario;
}
