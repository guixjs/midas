package com.residencia.backend.modules.dto.categoria;

import com.residencia.backend.modules.enums.TipoTransacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para criação de categorias")
public class CategoriaDTO {
  @Schema(description = "Nome da nova categoria", example = "Videogame")
  private String nome;
  @Schema(description = "Descrição da nova categoria", example = "Despesas feitas com jogos digitais")
  private String descricao;
  @Schema(description = "Tipo de transação da categoria",example = "DESPESA")
  private TipoTransacao tipoTransacao;
  @Schema(description = "Cor que representa a categoria (em Hexadecimal)",example = "#FF5733")
  private String cor;
}
