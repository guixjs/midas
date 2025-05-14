package com.residencia.backend.setup;

import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CategoriaInitSetup {
  @Bean
  CommandLineRunner initCategorias(CategoriaRepository categoriaRepository) {
    return args -> {
      if (categoriaRepository.count() == 0) {
        CategoriaEntity c1 = new CategoriaEntity();
        c1.setNome("Lazer");
        c1.setDescricao("Despesas com entretenimento");
        c1.setTipoTransacao(TipoTransacao.DESPESA);
        c1.setIdUsuario(null);

        CategoriaEntity c2 = new CategoriaEntity();
        c2.setNome("Alimentação");
        c2.setDescricao("Despesas com comida e bebidas");
        c2.setTipoTransacao(TipoTransacao.DESPESA);
        c2.setIdUsuario(null);

        CategoriaEntity c3 = new CategoriaEntity();
        c3.setNome("Transporte");
        c3.setDescricao("Despesas com locomoção");
        c3.setTipoTransacao(TipoTransacao.DESPESA);
        c3.setIdUsuario(null);

        CategoriaEntity c4 = new CategoriaEntity();
        c4.setNome("Mercado");
        c4.setDescricao("Despesas com compras de supermercado");
        c4.setTipoTransacao(TipoTransacao.DESPESA);
        c4.setIdUsuario(null);

        CategoriaEntity c5 = new CategoriaEntity();
        c5.setNome("Saúde");
        c5.setDescricao("Despesas com cuidados médicos e remédios");
        c5.setTipoTransacao(TipoTransacao.DESPESA);
        c5.setIdUsuario(null);

        CategoriaEntity c6 = new CategoriaEntity();
        c6.setNome("Freelance");
        c6.setDescricao("Receitas obtidas com trabalhos autônomos");
        c6.setTipoTransacao(TipoTransacao.RECEITA);
        c6.setIdUsuario(null);

        categoriaRepository.saveAll(Arrays.asList(c1, c2, c3,c4,c5,c6));
      }
    };
  }
}
