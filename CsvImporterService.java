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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        reader.readLine(); // pula o cabeçalho

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        UUID idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());

        while ((linha = reader.readLine()) != null) {
            String[] campos = linha.split(",");

            if (campos.length >= 5) {
                LocalDate data = LocalDate.parse(campos[0], formatter);
                BigDecimal valor = new BigDecimal(campos[1].trim());
                String descricao = campos[2].trim();
                CategoriaTemp categoria = CategoriaTemp.valueOf(campos[3].trim().toUpperCase());
                TipoTransacao tipo = TipoTransacao.valueOf(campos[4].trim().toUpperCase());

                // Aplica regra de valor negativo para débito
                if (tipo == TipoTransacao.DEBITO && valor.compareTo(BigDecimal.ZERO) > 0) {
                    valor = valor.negate();
                }

                TransacaoEntity transacao = TransacaoEntity.builder()
                        .data_transacao(data)
                        .valor(valor)
                        .descricao(descricao)
                        .categoria(categoria)
                        .tipoTransacao(tipo)
                        .id_usuario(idUsuario)
                        .build();

                transacoes.add(transacao);
            }
        }

        transacaoRepository.saveAll(transacoes);
    }
}
