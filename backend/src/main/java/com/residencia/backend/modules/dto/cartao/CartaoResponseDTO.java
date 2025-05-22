package com.residencia.backend.modules.dto.cartao;

import com.residencia.backend.modules.dto.conta.ContaResponseResumidoDTO;
import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
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
public class CartaoResponseDTO {
  private UUID id;
  private String nome;
  private LocalDate dataVencimento;
  private String cor;
  private ContaResponseResumidoDTO conta;
  private UsuarioResponseResumidoDTO usuario;
}
