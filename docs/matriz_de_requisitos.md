# Matriz de Requisitos - EduVault (App Zero-Knowledge)

Esta matriz consolida todos os requisitos do projeto após a decisão de adotar a arquitetura "Zero-Knowledge no Cliente" (onde o celular do usuário criptografa o dado antes de enviá-lo pela rede).

## 1. Requisitos de Negócio (RN)
| ID | Descrição | Prioridade |
|----|-----------|------------|
| **RN01** | O sistema deve coletar e armazenar dados sensíveis de estudantes (CPF, Nome, Data de Nascimento e Telefone) de forma ultra-segura. | Alta |
| **RN02** | O backend nunca deve receber o dado em texto claro no momento da gravação, garantindo que a ofuscação ocorra na origem (no aparelho do usuário). | Alta |
| **RN03** | O sistema deve possuir uma área administrativa para auditoria, onde usuários autorizados possam visualizar os dados descriptografados. | Média |

## 2. Requisitos Funcionais (RF)
| ID | Descrição | Dependência | Status |
|----|-----------|-------------|--------|
| **RF01** | A API deve possuir um endpoint de Autenticação (Login) que retorne um token JWT. | - | Concluído |
| **RF02** | A API deve expor um endpoint público que retorne a sua Chave Pública RSA (`GET /api/crypto/public-key`). | - | Pendente |
| **RF03** | O App Android deve realizar o login e armazenar o JWT com segurança. | RF01 | Pendente |
| **RF04** | O App Android deve gerar uma chave simétrica AES dinamicamente para cada salvamento. | - | Pendente |
| **RF05** | O App Android deve empacotar e criptografar o JSON usando a chave AES, e trancar essa chave usando a Chave Pública RSA da API. | RF02, RF04 | Pendente |
| **RF06** | O App Android deve enviar o Envelope Criptografado (`PUT /students/me`) para a API. | RF05 | Pendente |
| **RF07** | A API deve apenas salvar o Envelope no Banco de Dados MongoDB, sem aplicar criptografia adicional. | - | Pendente |
| **RF08** | A API deve descriptografar o Envelope no momento do retorno (`GET /students/me`) usando sua Chave Privada RSA, entregando o JSON em texto claro protegido pelo túnel HTTPS. | - | Pendente |

## 3. Requisitos Não Funcionais (RNF)
| ID | Descrição |
|----|-----------|
| **RNF01** | **Performance:** A geração da chave AES e a encriptação no celular não devem travar a interface do usuário (UI Thread). |
| **RNF02** | **Tecnologia App:** O App Android deve ser construído de forma nativa utilizando Kotlin e Jetpack Compose. |
| **RNF03** | **Persistência:** O banco de dados escolhido é o MongoDB devido à flexibilidade de armazenamento do Envelope (Documento BSON opaco). |

## 4. Requisitos de Segurança (RS)
| ID | Descrição |
|----|-----------|
| **RS01** | **Criptografia Simétrica:** Uso obrigatório de `AES-256-GCM` para garantir confidencialidade e integridade (Autenticação do Ciphertext via TAG). |
| **RS02** | **Criptografia Assimétrica:** Uso obrigatório de `RSA/ECB/OAEPPadding` para a proteção da chave AES. |
| **RS03** | **Ownership Check:** A API deve validar a identidade do usuário através da claim `sub` do token JWT para garantir que nenhum aluno acesse o documento de outro (Evitar Insecure Direct Object Reference - IDOR). |
