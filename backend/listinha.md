# MIDAS

## TRANSACOES

### Cadastro
- [x] Cadastrar manualmente
- [ ] Cadastrar via CSV

### Edição
- [ ] Editar transação
- [ ] Excluir transações

### Listagem
- [ ] Listar todas as transações
- [ ] Filtrar transações por:
    - [x] Categoria
    - [x] Data
    - [ ] Conta
    - [ ] Cartão
    - [ ] Tipo

### Regras de Relacionamento
- [x] Toda transação pertence a uma conta
- [ ] Uma transação pode pertencer a um cartão, desde que tenha uma conta

---

## CATEGORIAS
- [ ] Editar categoria das transações
- [ ] Editar categorias do sistema
- [x] Criar novas categorias
- [ ] Excluir categorias criadas
- [ ] Listar todas as categorias

---

## CONTAS
- [x] Criar contas com nomes diferentes
- [x] Criar conta "Geral" automaticamente ao autenticar-se
- [ ] Listar contas
- [ ] Excluir contas

---

## CARTÕES
- [x] Criar cartão
- [x] Vincular cartão a uma conta **diferente** da "Geral"

---
### !POSSIVEIS FUNCIONALIDADES!
- [ ] transações com cartão podem ter parcelas
- [ ] Permitir a importação de uma planilha própria (através da descrição da planilha pelo usúario)
---

# Correções

- [ ] Corrigir o tipo da transação (crédito > receita / débito > despesa)
- [ ] corrigir: ao passar um cartoa inexistente no body da transacao ele não fala qual é o erro, só dá 403
- [ ] Corrigir erro: "dataVencimento=12/03/2025" (O vencimento é um dia específico? Não deveria ser todo mês?)

## HOJE
- [x] Criar os mappers para cada entidade, com os métodos:
----
 - [x] **toEntity** (passa de dto para entity, usado do controller para o service)
 - [x] **toResponseDTO** (faz o build do response da entidade, só será usado nos services da propria entidade)
 - [x] **toResponseResumidoDTO** (faz o build do response resumido, será usado nas entidades com relacionamento)
  -----
  - [x] Usuário
  - [x] Conta
  - [x] Cartão
  - [x] Categoria
  - [x] Transação

### Corrgir
- [x] não aparece o erro: valor nulo transacao 
- [x] não aparece o erro: tipo nulo transacao
  (estão resolvidos mas deve ter uma forma melhor de corrigir isso)


