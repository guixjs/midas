# 💰 MIDAS – Sistema de Gestão Financeira

MIDAS é um sistema de controle financeiro pessoal, desenvolvido para ajudar usuários a registrarem e acompanharem suas receitas e despesas. O sistema permite o cadastro de transações, categorias, contas e cartões, além de análises úteis como principais gastos, saldo total e transações por período.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Maven
- JPA / Hibernate
- H2 Database (default)
- Docker (opcional)
---

## ⚙️Requisitos
1. Java 17(ou superior)
2. Docker (opcional)

## 📦 Como Executar o Projeto

### Clonando o repositório

```bash
git clone https://github.com/guixjs/midas.git
```
### Baixando as dependências
```bash
cd backend
./mvnw install
./mvnw spring-boot:run

```

---
## 📬 Coleção Insomnia (API REST)

O projeto contém um arquivo de exportação do Insomnia com todas as requisições da API.

### ▶️ Como importar:

1. Abra o Insomnia
2. Vá em `File > Import`
3. Selecione `From File`
4. Escolha o arquivo: `insomnia/midas-insomnia(temporario).har`

Isso vai importar todas as rotas prontas para testar a API localmente ou em produção.

### ⚠️ Atenção:
Apenas as rotas 

`/user/new` e `/user/auth` não precisam de autenticação,
as outras precisam do *Bearer token* no auth da requisição

---

## 🐳 Usando Docker com PostgreSQL (opcional)
### Caso deseje usar Docker:

1. No arquivo application.properties, comente as configurações do H2

2. Descomente as configurações do PostgreSQL

3. Suba o container com:

```bash
docker-compose up -d
```