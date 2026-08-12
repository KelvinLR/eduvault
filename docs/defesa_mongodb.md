# Defesa Arquitetural: Por que MongoDB?

A decisão de utilizar o **MongoDB** (um banco de dados NoSQL Orientado a Documentos) como repositório principal de dados do EduVault não foi por acaso. O cenário imposto pelo projeto (onde os dados são pacotes blindados) favorece imensamente este paradigma em relação aos tradicionais bancos SQL (Relacionais como PostgreSQL ou MySQL).

Abaixo, detalhamos as principais justificativas para esta escolha:

## 1. O Padrão "Envelope" quebra o Modelo Relacional
Quando aplicamos **Application-Level Encryption** (onde o Backend criptografa o payload inteiro antes de salvar), nós perdemos a principal vantagem de um banco de dados Relacional: **As Consultas e os Índices.**
- Em um banco SQL, criaríamos colunas como `nome`, `cpf`, `data_nascimento`.
- Com a criptografia de payload, não podemos fazer `SELECT * FROM students WHERE nome = 'Kelvin'`, pois o nome no banco é um Base64 ilegível.
- Em bancos relacionais, armazenar um Envelope JSON opaco (BLOB/JSONB) frequentemente quebra a Primeira Forma Normal (1NF). 

No MongoDB, a estrutura de documento casa **perfeitamente** com o armazenamento de envelopes. Salvamos um documento BSON cujo conteúdo é simplesmente `{ "userId": "...", "encryptedPayload": { ... } }`, sem sofrer com schemas engessados de SQL que perdem seu propósito quando o dado está ofuscado.

## 2. Schema Flexibility (Flexibilidade de Esquema)
A natureza da criptografia de payloads significa que a estrutura real dos dados sensíveis muda **apenas na aplicação** (JSON criptografado).
Se o EduVault amanhã decidir que precisa salvar também a "tipagem sanguínea" do aluno, no MongoDB isso não requer nenhum `ALTER TABLE`. Basta que a aplicação adicione o campo no JSON, criptografe e salve o novo Envelope no MongoDB. O banco de dados se mantém alheio e flexível às constantes mudanças de negócio dentro da caixa preta da criptografia.

## 3. Escalabilidade Horizontal
O MongoDB é desenhado para escalar nativamente por meio de *Sharding*. 
Ao adotar o MongoDB, o EduVault está preparado para distribuir terabytes de payloads criptografados em diversos servidores de banco de dados (clusters), distribuindo a carga de gravação intensiva de forma mais simples que clusters relacionais.

## 4. Agilidade na Construção de PoCs (Provas de Conceito)
O foco desta etapa inicial do EduVault é comprovar o motor de Criptografia Híbrida ponta-a-ponta e não lidar com mapeamentos Objeto-Relacional (ORM) complexos ou gerenciar scripts de migração (Flyway/Liquibase). O uso do MongoDB, integrado ao `Spring Data MongoDB`, fornece uma velocidade de iteração incomparável.

## Conclusão
O PostgreSQL seria brilhante se precisássemos fazer *joins*, agregações analíticas nos dados dos alunos ou aplicar integridade referencial pesada. 
No entanto, no nosso contexto, **os dados sensíveis dos alunos são pacotes fechados (envelopes)**, que não podem e não devem ser consultados pelo banco de dados por seu conteúdo, apenas transportados e entregues a quem tem a chave de acesso. O MongoDB é a ferramenta de transporte e armazenamento de documentos ideal para essa filosofia.
