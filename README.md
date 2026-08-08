# EduVault — PoC de Proteção de Dados Sensíveis

> **Proof of Concept** educacional que demonstra criptografia híbrida (AES-256-GCM + RSA-OAEP) em uma aplicação Android integrada com Spring Boot e MongoDB.

## Objetivo

Demonstrar que um aplicativo Android pode **criptografar dados sensíveis antes do envio**, armazená-los criptografados em um banco de documentos e permitir que **somente usuários autorizados** recuperem os dados originais.

## Stack

| Camada   | Tecnologia                         |
|----------|------------------------------------|
| Mobile   | Android / Kotlin / Jetpack Compose |
| Backend  | Java 21 / Spring Boot / Maven      |
| Banco    | MongoDB 7                          |
| Crypto   | AES-256-GCM + RSA-OAEP             |
| Auth     | Spring Security + JWT              |

## Quick Start

```bash
# 1. Subir MongoDB
docker compose up -d

# 2. Gerar chaves RSA (apenas na primeira vez)
cd backend/keys
openssl genpkey -algorithm RSA -out rsa_private.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in rsa_private.pem -out rsa_public.pem

# 3. Rodar backend
cd backend
mvn spring-boot:run

# 4. Abrir android/ no Android Studio
```

## Estrutura

```
eduvault/
├── backend/       # Spring Boot REST API
├── android/       # App Android (Kotlin + Compose)
├── docs/          # Documentação
└── docker-compose.yml
```
