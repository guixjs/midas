package com.residencia.backend.modules.services.transacao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CsvImporterService {

    private final TransacaoRepository transacaoRepository;
    private final CategoriaRepository categoriaRepository;

    public CsvImporterService(TransacaoRepository transacaoRepository, CategoriaRepository categoriaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public void importarCSV(MultipartFile file, HttpServletRequest request) throws Exception {
        List<TransacaoEntity> transacoes = new ArrayList<>();
        UUID idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
                .withSkipLines(0)
                .build();

        List<String[]> linhas = ((com.opencsv.CSVReader) reader).readAll();
        if (linhas.isEmpty()) return;

        String[] cabecalho = linhas.get(0);
        Map<String, Integer> indices = detectarIndices(cabecalho);

        int linhasImportadas = 0;
        int linhasIgnoradas = 0;

        for (int i = 1; i < linhas.size(); i++) {
            String[] campos = linhas.get(i);
            try {
                LocalDate data;
                BigDecimal valor;
                String descricao;
                String nomeCategoria;

                if (!indices.isEmpty()) {
                    data = LocalDate.parse(campos[indices.get("data")].trim(), formatter);
                    valor = new BigDecimal(campos[indices.get("valor")].trim().replace("R$", "").replace(",", "."));
                    descricao = campos[indices.get("descricao")].trim();
                    nomeCategoria = campos[indices.get("categoria")].trim();
                } else {
                    data = LocalDate.parse(campos[0].trim(), formatter);
                    valor = new BigDecimal(campos[campos.length - 1].trim().replace("R$", "").replace(",", "."));
                    descricao = campos.length >= 3 ? campos[2].trim() : "Descrição desconhecida";
                    nomeCategoria = "Importado";
                }

                TipoTransacao tipo = valor.compareTo(BigDecimal.ZERO) >= 0 ? TipoTransacao.RECEITA : TipoTransacao.DEBITO;
                CategoriaEntity categoria = categoriaRepository.findByNomeAndIdUsuario(nomeCategoria, idUsuario)
                        .orElseGet(() -> categoriaRepository.save(
                                CategoriaEntity.builder()
                                        .nome(nomeCategoria)
                                        .idUsuario(idUsuario)
                                        .descricao("Criada automaticamente pelo importador")
                                        .build()
                        ));

                TransacaoEntity transacao = TransacaoEntity.builder()
                        .data_transacao(data)
                        .valor(valor)
                        .descricao(descricao)
                        .tipoTransacao(tipo)
                        .categoria(categoria)
                        .idUsuario(idUsuario)
                        .build();

                transacoes.add(transacao);
                linhasImportadas++;
            } catch (Exception e) {
                linhasIgnoradas++;
//                e.printStackTrace();
            }
        }

        transacaoRepository.saveAll(transacoes);
        System.out.printf("Importação finalizada. Transações importadas: %d | Ignoradas: %d%n", linhasImportadas, linhasIgnoradas);
    }

    private Map<String, Integer> detectarIndices(String[] colunas) {
        Map<String, Integer> indices = new HashMap<>();

        for (int i = 0; i < colunas.length; i++) {
            String col = colunas[i].toLowerCase().trim();
            if ((col.contains("data") || col.contains("lançamento")) && !indices.containsKey("data")) {
                indices.put("data", i);
            } else if (col.contains("valor") && !indices.containsKey("valor")) {
                indices.put("valor", i);
            } else if ((col.contains("descrição") || col.contains("descricao") || col.contains("histórico")) && !indices.containsKey("descricao")) {
                indices.put("descricao", i);
            } else if (col.contains("categoria") && !indices.containsKey("categoria")) {
                indices.put("categoria", i);
            }
        }

        return indices;
    }
}


