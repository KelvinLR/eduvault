# Arquitetura Criptográfica do EduVault

Este documento detalha o mecanismo de criptografia adotado no projeto EduVault, as escolhas arquiteturais e o comparativo com outras soluções de mercado.

## O Mecanismo: Criptografia Híbrida (AES + RSA)
Para garantir que o banco de dados tenha **Conhecimento Zero (Zero-Knowledge)** sobre os dados reais dos alunos, implementamos uma Criptografia Híbrida em Nível de Aplicação.

### Como Funciona o Fluxo:
1. **AES-256-GCM (Simétrica):** Quando um JSON (nome, cpf, etc) chega na API, geramos dinamicamente uma chave simétrica AES ultra-rápida de 256 bits. O AES-GCM criptografa o JSON inteiro, transformando-o em um bloco ilegível (`ciphertext`) e gera um Vetor de Inicialização (`IV`).
2. **RSA-OAEP (Assimétrica):** Como não podemos deixar a chave AES solta no banco, utilizamos a **Chave Pública RSA** do servidor para criptografar a própria chave AES.
3. **Envelope Criptográfico:** O banco de dados salva apenas o Envelope: `{ ciphertext, iv, encryptedAesKey }`. 
4. **Recuperação:** Para ler o dado, o backend usa a sua **Chave Privada RSA** para destrancar a chave AES, e então usa a chave AES para revelar o JSON original.

---

## Comparativo com Outros Mecanismos

### 1. Criptografia Híbrida vs. Hashing (Bcrypt/Argon2)
- **O que é Hashing:** Funções matemáticas de mão única. Usadas para proteger senhas.
- **Por que não usamos:** O Hash é irreversível. O EduVault precisa ler o Nome e o CPF do usuário para exibi-los no painel de volta para o próprio aluno. Logo, precisamos de criptografia reversível (Cifragem), não hashing.

### 2. Criptografia Híbrida vs. Apenas Criptografia Simétrica (AES puro)
- **O que é AES puro:** Usar uma única "Chave Mestra" (Master Key) no código fonte para criptografar o banco inteiro.
- **O problema:** Se a Master Key vazar (ex: um desenvolvedor a subir no GitHub acidentalmente ou ela for lida no servidor), **todo o banco de dados é comprometido de uma só vez**. 
- **Nossa Vantagem:** Usando Criptografia Híbrida, cada estudante no banco de dados é criptografado com uma chave AES única e dinâmica. Apenas a chave RSA Privada (que pode ser rotacionada ou guardada em HSM/KMS de nuvem) pode destrancar esses cofres individuais, reduzindo drasticamente o raio de explosão (blast radius) de um vazamento.

### 3. Criptografia Híbrida vs. Apenas Criptografia Assimétrica (RSA puro)
- **O que é RSA puro:** Usar apenas a Chave Pública para encriptar o dado inteiro.
- **O problema:** O RSA é **extremamente lento** e possui limites estritos de tamanho (não pode criptografar payloads maiores que o tamanho da chave, ex: um JSON de 2KB).
- **Nossa Vantagem:** O AES lida com o tamanho (podendo criptografar megabytes num piscar de olhos), enquanto o RSA cuida de proteger apenas a chave (que é pequenininha, 32 bytes). Unimos o melhor dos dois mundos.

### 4. Criptografia no Nível da Aplicação vs. TDE (Transparent Data Encryption)
- **O que é TDE:** Mecanismo nativo de bancos de dados (como PostgreSQL, Oracle ou MongoDB Enterprise) que criptografa os arquivos do banco de dados no HD (Criptografia at Rest).
- **O problema do TDE:** O TDE protege apenas contra o roubo físico do HD. Se a máquina estiver ligada e o banco estiver rodando, qualquer consulta SQL/NoSQL no banco retornará os dados em texto claro. Se um Hacker vazar o banco via Injeção de SQL/NoSQL ou credenciais comprometidas, o TDE é inútil.
- **Nossa Vantagem (Application-Level Encryption):** No EduVault, o dado já chega criptografado no banco de dados. Mesmo que o DBA admin do banco de dados tente ler a tabela de estudantes, ele só verá Base64. A inteligência de destrancar reside exclusivamente na Aplicação Backend, mantendo o verdadeiro Zero-Knowledge no banco.
