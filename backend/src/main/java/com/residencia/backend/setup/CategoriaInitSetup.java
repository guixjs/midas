package com.residencia.backend.setup;

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
        c1.setId_usuario(null);

        CategoriaEntity c2 = new CategoriaEntity();
        c2.setNome("Alimentação");
        c2.setDescricao("Despesas com comida e bebidas");
        c2.setId_usuario(null);

        CategoriaEntity c3 = new CategoriaEntity();
        c3.setNome("Transporte");
        c3.setDescricao("Despesas com locomoção");
        c3.setId_usuario(null);

        categoriaRepository.saveAll(Arrays.asList(c1, c2, c3));
      }
    };
  }
}
