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
- [x] Está sendo possível, cadastrar uma transação com cartão sem conta (conta do cartão e conta da transação diferentes)
- [x] corrigir: cadastrar dois cartões com o mesmo nome
- [ ] corrigir: ao passar um cartoa inexistente no body da transacao ele não fala qual é o erro, só dá 403
- [ ] corrigir: ao criar uma conta sem autenticação, ele executa mesmo sem salvar no bd (nunca irá acontecer)
- [ ] corrigir: conta e cartao estão com o usuario null, precisa passar no response builder

### CRIAR REGRA DE NEGÓCIO CONTA
- [x] Não permitir criar contas com o mesmo nome

### CRIAR REGRA DE NEGÓCIO CARTÃO
- [ ] Corrigir erro: "dataCriacao=null"
- [ ] Corrigir erro: "dataVencimento=12/03/2025" (O vencimento é um dia específico? Não deveria ser todo mês?)

### CRIAR UM RESPONSE DTO PARA AS ENTIDADES QUE FALTAM
- [x] Usuário
- [x] Conta
- [x] Cartão
  - [ ] Usar mapper para mapear cada um
  - [ ] substituir em todos os lugares que usa builder
  - [x] Colocar idConta e idCartão no response de transação

### !POSSIVEIS FUNCIONALIDADES!
- [ ] transações com cartão podem ter parcelas
- [ ] Permitir a importação de uma planilha própria (através da descrição da planilha pelo usúario)

## HOJE
- [x] refatorar responsesDTO, incluir IDs das entidades relacionadas e do objeto criado 
- [x] corrigir "data criação =null" (cartão, conta e onde estiver)
- [x] refatorar transacao, várias verificações estão sendo feitas no service, devo criar uma classe "validator" e injeta-la no service
- [ ] montar o builder certo para conta e cartão (usuario está nulo)
- [ ] erro no response do cartão e conta
- [x] erro na criação de um cartão com nome repetido não retorna o erro 400
- [ ] corrigir response do usuario 
- [ ] criar os mappers para cada dto, resolvendo o erro da montagem do response no serviece ou controller
- [ ] corrigir a lógica de data da transacao (caso n informado, hoje (ou isso é trabalho pro front?))
- [ ] criar as novas categorias fixas
- [ ] tornar repositório publico
- [ ] testar se o codigo funciona só com o postgre (sem docker)
