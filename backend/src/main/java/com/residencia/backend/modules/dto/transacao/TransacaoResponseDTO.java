package com.residencia.backend.modules.dto.transacao;

import com.residencia.backend.modules.dto.cartao.CartaoResponseResumidoDTO;
import com.residencia.backend.modules.dto.categoria.CategoriaResponseResumidoDTO;
import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
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
  private BigDecimal valor;
  private String descricao;
  private LocalDate dataTransacao;
  private TipoTransacao tipoTransacao;

  private CategoriaResponseResumidoDTO categoria;
  private ContaResponseResumidoDTO conta;
  private CartaoResponseResumidoDTO cartao;
  private UsuarioResponseResumidoDTO usuario;
}
