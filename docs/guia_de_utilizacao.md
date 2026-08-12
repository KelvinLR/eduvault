# Guia de Utilização - Backend EduVault

Este guia detalha como executar, testar e interagir com a API do EduVault localmente.

## Pré-requisitos
- **Java 17** instalado.
- **Docker e Docker Compose** instalados (para o banco de dados).
- **Python 3** (Opcional, apenas para rodar o script de teste automatizado).

## 1. Subindo a Infraestrutura (Banco de Dados)
O EduVault utiliza o MongoDB. Para iniciar o banco de dados via Docker, abra o terminal na raiz do projeto e execute:
```bash
docker-compose up -d
```
O banco rodará na porta `27017` em background.

## 2. Executando a API (Spring Boot)
Na pasta `/backend`, utilize o Maven Wrapper para iniciar a aplicação sem precisar instalar o Maven globalmente:
```bash
cd backend
./mvnw spring-boot:run
```
A API ficará disponível em `http://localhost:8080`.

## 3. Endpoints Disponíveis

### Autenticação
- **`POST /auth/login`**
  - **Body:** `{"username": "student01", "password": "123456"}`
  - **Retorno:** Token JWT.

### Área do Estudante
*Requer Header: `Authorization: Bearer <TOKEN_AQUI>`*
- **`PUT /students/me`**
  - **Descrição:** Salva ou atualiza os dados sensíveis do aluno autenticado. A API se encarregará de **criptografar** tudo antes de salvar no banco.
  - **Body:** 
    ```json
    {
      "name": "João Silva",
      "cpf": "111.222.333-44",
      "birthDate": "01/01/2000",
      "phone": "(11) 99999-9999"
    }
    ```
- **`GET /students/me`**
  - **Descrição:** Retorna os dados do próprio aluno. A API buscará o Envelope no banco, o **descriptografará** e entregará o JSON em texto claro.

### Área Administrativa
*Requer token JWT de um usuário com Role `ADMIN` (ex: username: `admin`, senha: `admin`)*
- **`GET /admin/students`**
  - **Descrição:** Lista todos os estudantes cadastrados. A API descriptografa os dados para visualização administrativa.

## 4. Rodando o Script de Teste Automatizado
Para facilitar, criamos um script Python (`test_api.py`) que simula o fluxo completo de um cliente:
1. Faz login;
2. Envia dados (que são criptografados);
3. Busca os dados (que são descriptografados).

Para executar:
```bash
cd backend
python3 test_api.py
```

## 5. Inspecionando o Banco de Dados
Para comprovar a segurança (Zero-Knowledge), você pode inspecionar o MongoDB para verificar que não há dados em texto claro salvos:
```bash
docker exec eduvault-mongo mongosh eduvault --eval 'db.students.find().pretty()'
```
