# MIDAS

## TRANSACOES

### Cadastro
- [x] Cadastrar manualmente
- [ ] Cadastrar via CSV
- [ ] Importar planilha de anotações

### Edição
- [ ] Editar transação
- [ ] Excluir transações

### Listagem
- [ ] Listar todas as transações
- [ ] Filtrar transações por:
    - [ ] Categoria
    - [ ] Data
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

# Correções

### REFATORAR TRANSAÇÃO
- [ ] Corrigir o tipo da transação (crédito > receita / débito > despesa)
- [ ] Marcar como "hoje" se a data não for informada
- [x] Relacionar transação com conta
- [x] Relacionar transação com cartão
- [ ] Está sendo possível, cadastrar uma transação com cartão sem conta (conta do cartão e conta da transação diferentes)

### CRIAR REGRA DE NEGÓCIO CONTA
- [x] Não permitir criar contas com o mesmo nome

### CRIAR REGRA DE NEGÓCIO CARTÃO
- [ ] Corrigir erro: "dataCriacao=null"
- [ ] Corrigir erro: "dataVencimento=12/03/2025" (O vencimento é um dia específico? Não deveria ser todo mês?)

### CRIAR UM RESPONSE DTO PARA AS ENTIDADES QUE FALTAM
- [ ] Usuário
- [ ] Conta
- [ ] Cartão
  - [ ] Usar mapper para mapear cada um
  - [ ] substituir em todos os lugares que usa builder
  - [ ] Colocar idConta e idCartão no response de transação

### !POSSIVEIS FUNCIONALIDADES!
- [ ] transações com cartão podem ter parcelas
- [ ] Permitir a importação de uma planilha própria (através da descrição da planilha pelo usúario)
