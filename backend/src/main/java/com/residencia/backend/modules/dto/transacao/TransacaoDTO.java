package com.residencia.backend.modules.dto.transacao;


import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.models.CategoriaEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransacaoDTO {
  private String descricao;
  private LocalDate data_transacao;
  private BigDecimal valor;
  private TipoTransacao tipoTransacao;
  private Integer id_categoria;
}

