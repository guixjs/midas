# 💰 MIDAS – Sistema de Gestão Financeira

MIDAS é um sistema de controle financeiro pessoal, desenvolvido 
para ajudar usuários a registrarem e acompanharem suas receitas e despesas. 
O sistema permite o cadastro de transações, categorias, contas, cartões ,
transações recorrentes e importação do extrato CSV .
Além de análises úteis como principais gastos, saldo total, transações por período, 
relação das categorias com mais gastos, entre outras funcionalidades.

---
## 🛜 Acesse a aplicação:
https://midas-steel-theta.vercel.app

---

## 🚀 Tecnologias Utilizadas
### Backend
- Java 17
- Spring boot 3.4.4
- PostgreSQL
### Frontend
- React 
- Typescript
- Next
### Conteinerização
- Docker
---


# 🖥️Como executar o projeto localmente

## 💾 1.Sem utilizar Docker

### ⚙️Requisitos
1. Java 17(ou superior)
2. Node.js
3. PostgreSQL


### Clonando o repositório

```bash
git clone https://github.com/guixjs/midas.git
```

### Navegando até a pasta do projeto
```bash
cd midas
```

### Executando o backend
```bash
cd backend
./mvnw install
./mvnw spring-boot:run
```
### Configurando o banco de dados

1. Crie um banco de dados PostegreSQL com o nome `midas`
2. Atualize a configuração do banco de dados em `application-prod.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/midas
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```
---

### Executando frontend
```bash
cd ..
cd frontend
npm install
npm run dev
```
**A aplicação ficará acessível no link http://localhost:3000**

### Também é possível executar o projeto usando o banco H2 (em memória):
1. Atualize a configuração do profile para `dev`em `application.properties`

---
## 🐳 2.Utilizando o docker

### ⚙️Requisitos
1. Docker

### Clonando o repositório
```bash
git clone https://github.com/guixjs/midas.git
```

### Navegando até a pasta do projeto
```bash
cd midas
```
### Fazendo build da aplicação com docker
```bash
docker compose up -d --build
```
**A aplicação ficará acessível no link http://localhost:3000**

---

#### As imagens `guixfelix/midas-docker-front` e `guixfelix/midas-docker-back`estão disponível no dockerhub

---