package com.residencia.backend.modules.dto.categoria;


import com.residencia.backend.modules.dto.usuario.UsuarioResponseResumidoDTO;
import com.residencia.backend.modules.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponseDTO {
  private Integer id;
  private String nome;
  private String descricao;
  private String cor;
  private TipoTransacao tipoTransacao;
  private UsuarioResponseResumidoDTO usuario;
}
