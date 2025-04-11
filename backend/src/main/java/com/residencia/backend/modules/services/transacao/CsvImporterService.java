package com.residencia.backend.modules.services.transacao;

import com.residencia.backend.modules.enums.CategoriaTemp;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CsvImporterService {

    private final TransacaoRepository transacaoRepository;

    public CsvImporterService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public void importarCSV(MultipartFile file, HttpServletRequest request) throws Exception {
        List<TransacaoEntity> transacoes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

        String linha;
        String separador = ",";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
        UUID idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());

        String cabecalho = reader.readLine();
        if (cabecalho.contains(";")) {
            separador = ";";
        }

        while ((linha = reader.readLine()) != null) {
            if (linha.isBlank()) continue;

            String[] campos = linha.split(separador);
            try {
                LocalDate data;
                BigDecimal valor;
                String descricao;

                if (cabecalho.contains("Identificador")) { // Nubank
                    data = LocalDate.parse(campos[0].trim(), formatter);
                    valor = new BigDecimal(campos[1].replace(",", "."));
                    descricao = campos[3].trim();

                } else if (cabecalho.contains("Histórico")) { // Banco Inter
                    data = LocalDate.parse(campos[0].trim(), formatter);
                    valor = new BigDecimal(campos[3].replace(",", "."));
                    descricao = campos[2].trim();
                } else {
                    continue; // formato não reconhecido
                }

                TipoTransacao tipo = valor.compareTo(BigDecimal.ZERO) >= 0 ? TipoTransacao.RECEITA : TipoTransacao.DEBITO;
                CategoriaTemp categoria = inferirCategoria(descricao);

                TransacaoEntity transacao = TransacaoEntity.builder()
                        .data_transacao(data)
                        .valor(valor)
                        .descricao(descricao)
                        .tipoTransacao(tipo)
                        .categoria(categoria)
                        .id_usuario(idUsuario)
                        .build();

                transacoes.add(transacao);
            } catch (Exception e) {
                // Ignora linha com erro
                e.printStackTrace();
            }
        }

        transacaoRepository.saveAll(transacoes);
    }

    private CategoriaTemp inferirCategoria(String descricao) {
        descricao = descricao.toLowerCase();
        if (descricao.contains("salário") || descricao.contains("recebida")) return CategoriaTemp.RENDA_FIXA;
        if (descricao.contains("pix") && descricao.contains("enviad")) return CategoriaTemp.PAGAMENTO;
        if (descricao.contains("supermercado") || descricao.contains("mercado")) return CategoriaTemp.ALIMENTACAO;
        if (descricao.contains("fatura") || descricao.contains("pagamento")) return CategoriaTemp.PAGAMENTO;
        return CategoriaTemp.OUTROS;
    }
}
