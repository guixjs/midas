package com.residencia.backend.modules.services.transacao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.residencia.backend.modules.enums.TipoTransacao;
import com.residencia.backend.modules.models.CategoriaEntity;
import com.residencia.backend.modules.models.ContaEntity;
import com.residencia.backend.modules.models.TransacaoEntity;
import com.residencia.backend.modules.repositories.CategoriaRepository;
import com.residencia.backend.modules.repositories.ContaRepository;
import com.residencia.backend.modules.repositories.TransacaoRepository;
import com.residencia.backend.shareds.util.CorUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class CsvImporterService {

    private final TransacaoRepository transacaoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ContaRepository contaRepository;

    public CsvImporterService(TransacaoRepository transacaoRepository, CategoriaRepository categoriaRepository, ContaRepository contaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
        this.contaRepository = contaRepository;
    }

    public void importarCSV(MultipartFile file, HttpServletRequest request) throws Exception {
        // Validar se o arquivo é CSV
        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("O arquivo deve ser do tipo CSV");
        }

        List<TransacaoEntity> transacoes = new ArrayList<>();
        UUID idUsuario = UUID.fromString(request.getAttribute("id_usuario").toString());
        
        // Obter a conta padrão do usuário (Geral)
        ContaEntity contaPadrao = contaRepository.findByIdUsuarioAndNome(idUsuario, "Padrão")
                .orElseThrow(() -> new RuntimeException("Conta padrão 'Padrão' não encontrada"));
        
        // Configurar leitores CSV
        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
                .withSkipLines(0)
                .build();

        List<String[]> linhas = reader.readAll();
        if (linhas.isEmpty()) return;

        String[] cabecalho = linhas.get(0);
        Map<String, Integer> indices = detectarIndices(cabecalho);
        
        // Lista de formatos de data para tentar
        List<DateTimeFormatter> formatadores = Arrays.asList(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
        );

        int linhasImportadas = 0;
        int linhasIgnoradas = 0;

        for (int i = 1; i < linhas.size(); i++) {
            String[] campos = linhas.get(i);
            
            // Ignorar linhas vazias
            if (campos.length == 0 || (campos.length == 1 && campos[0].trim().isEmpty())) {
                continue;
            }
            
            try {
                LocalDate data = null;
                BigDecimal valor = null;
                String descricao = "Sem descrição";
                String nomeCategoria = "Importado";

                // Tentar extrair dados com base nos índices detectados
                if (!indices.isEmpty()) {
                    // Tentar diferentes formatos de data
                    String dataStr = indices.containsKey("data") && indices.get("data") < campos.length ? 
                        campos[indices.get("data")].trim() : "";
                    data = parseData(dataStr, formatadores);
                    
                    // Processar valor
                    if (indices.containsKey("valor") && indices.get("valor") < campos.length) {
                        String valorStr = campos[indices.get("valor")].trim()
                                .replace("R$", "")
                                .replace("$", "")
                                .trim();
                        
                        // Detectar formato do valor
                        if (valorStr.contains(",")) {
                            // Formato brasileiro (1.234,56)
                            valorStr = valorStr.replace(".", "").replace(",", ".");
                        } else if (valorStr.contains(".")) {
                            // Formato americano (1,234.56) - não fazer nada, já está no formato correto para BigDecimal
                        }
                        
                        if (!valorStr.isEmpty()) {
                            try {
                                valor = new BigDecimal(valorStr);
                            } catch (NumberFormatException e) {
                                System.out.println("Erro ao converter valor: " + valorStr);
                            }
                        }
                    }
                    
                    // Processar descrição e categoria
                    descricao = indices.containsKey("descricao") && indices.get("descricao") < campos.length ? 
                        campos[indices.get("descricao")].trim() : descricao;
                    nomeCategoria = indices.containsKey("categoria") && indices.get("categoria") < campos.length ? 
                        campos[indices.get("categoria")].trim() : nomeCategoria;
                } else {
                    // Tentar extrair dados sem índices específicos
                    // Assumir que a primeira coluna é a data
                    if (campos.length > 0) {
                        data = parseData(campos[0].trim(), formatadores);
                    }
                    
                    // Assumir que a última coluna é o valor
                    if (campos.length > 1) {
                        String valorStr = campos[campos.length - 1].trim()
                                .replace("R$", "")
                                .replace("$", "")
                                .trim();
                        
                        // Detectar formato do valor
                        if (valorStr.contains(",")) {
                            // Formato brasileiro (1.234,56)
                            valorStr = valorStr.replace(".", "").replace(",", ".");
                        } else if (valorStr.contains(".")) {
                            // Formato americano (1,234.56) - não fazer nada, já está no formato correto para BigDecimal
                        }
                        
                        if (!valorStr.isEmpty()) {
                            try {
                                valor = new BigDecimal(valorStr);
                            } catch (NumberFormatException e) {
                                System.out.println("Erro ao converter valor: " + valorStr);
                            }
                        }
                    }
                    
                    // Tentar extrair descrição
                    if (campos.length >= 3) {
                        descricao = campos[1].trim();
                    }
                }
                
                // Verificar se os dados essenciais foram extraídos
                if (data == null || valor == null) {
                    throw new IllegalArgumentException("Não foi possível extrair data ou valor da linha");
                }

                // Determinar o tipo de transação
                TipoTransacao tipo = valor.compareTo(BigDecimal.ZERO) >= 0 ? TipoTransacao.RECEITA : TipoTransacao.DESPESA;
                
                // Criar uma variável final para usar na lambda
                final String categoriaNome = nomeCategoria;
                
                // Buscar ou criar categoria
                CategoriaEntity categoria = categoriaRepository.findByNomeAndIdUsuario(categoriaNome, idUsuario)
                        .orElseGet(() -> categoriaRepository.save(
                                CategoriaEntity.builder()
                                        .nome(categoriaNome)
                                        .tipoTransacao(tipo)
                                        .idUsuario(idUsuario)
                                        .descricao("Criada automaticamente pelo importador")
                                        .cor(CorUtil.corAletoria())
                                        .build()
                        ));

                // Criar transação
                TransacaoEntity transacao = TransacaoEntity.builder()
                        .dataTransacao(data)
                        .valor(valor)
                        .descricao(descricao)
                        .tipoTransacao(tipo)
                        .idCategoria(categoria.getId())
                        .idConta(contaPadrao.getId())
                        .idUsuario(idUsuario)
                        .build();

                transacoes.add(transacao);
                linhasImportadas++;
            } catch (Exception e) {
                linhasIgnoradas++;
                System.out.println("Erro ao processar linha " + i + ": " + e.getMessage());
            }
        }

        // Salvar transações
        if (!transacoes.isEmpty()) {
            transacaoRepository.saveAll(transacoes);
        }
        
        System.out.printf("Importação finalizada. Transações importadas: %d | Ignoradas: %d%n", linhasImportadas, linhasIgnoradas);
        
        if (linhasImportadas == 0) {
            throw new RuntimeException("Nenhuma transação foi importada. Verifique o formato do arquivo CSV.");
        }
    }

    private LocalDate parseData(String dataStr, List<DateTimeFormatter> formatadores) {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            return null;
        }
        
        for (DateTimeFormatter formatter : formatadores) {
            try {
                return LocalDate.parse(dataStr, formatter);
            } catch (DateTimeParseException e) {
                // Continuar tentando com o próximo formatador
            }
        }
        
        throw new IllegalArgumentException("Formato de data não reconhecido: " + dataStr);
    }

    private Map<String, Integer> detectarIndices(String[] colunas) {
        Map<String, Integer> indices = new HashMap<>();

        for (int i = 0; i < colunas.length; i++) {
            String col = colunas[i].toLowerCase().trim();
            
            // Detectar coluna de data
            if ((col.contains("data") || col.contains("lançamento") || col.contains("lancamento") || 
                 col.contains("date") || col.contains("dt")) && !indices.containsKey("data")) {
                indices.put("data", i);
            } 
            // Detectar coluna de valor
            else if ((col.contains("valor") || col.contains("value") || col.contains("amount") || 
                     col.contains("price") || col.contains("preço") || col.contains("preco")) && 
                     !indices.containsKey("valor")) {
                indices.put("valor", i);
            } 
            // Detectar coluna de descrição
            else if ((col.contains("descrição") || col.contains("descricao") || col.contains("histórico") || 
                     col.contains("historico") || col.contains("description") || col.contains("memo") || 
                     col.contains("detalhe")) && !indices.containsKey("descricao")) {
                indices.put("descricao", i);
            } 
            // Detectar coluna de categoria
            else if ((col.contains("categoria") || col.contains("category") || col.contains("tipo") || 
                     col.contains("type")) && !indices.containsKey("categoria")) {
                indices.put("categoria", i);
            }
        }

        return indices;
    }
}


