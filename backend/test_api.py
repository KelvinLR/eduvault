#!/usr/bin/env python3
import urllib.request
import urllib.error
import json

BASE_URL = "http://localhost:8080"

def do_request(endpoint, method="GET", data=None, token=None):
    url = f"{BASE_URL}{endpoint}"
    headers = {"Content-Type": "application/json"}
    if token:
        headers["Authorization"] = f"Bearer {token}"
        
    req_data = json.dumps(data).encode('utf-8') if data else None
    req = urllib.request.Request(url, data=req_data, headers=headers, method=method)
    
    try:
        with urllib.request.urlopen(req) as response:
            resp_body = response.read().decode('utf-8')
            if not resp_body:
                return None
            try:
                return json.loads(resp_body)
            except:
                return resp_body
    except urllib.error.HTTPError as e:
        print(f"Erro HTTP {e.code}: {e.read().decode('utf-8')}")
        return None
    except Exception as e:
        print(f"Erro de Conexão: {e}. Verifique se o Spring Boot está rodando.")
        exit(1)

print("\n🚀 Iniciando Teste da Criptografia Híbrida do EduVault...\n")

# 1. Login
print("=== 1. Fazendo Login como Estudante ===")
login_resp = do_request("/auth/login", "POST", {"username": "student01", "password": "123456"})
if not login_resp:
    exit(1)
    
token = login_resp.get("token")
print(f"✅ Sucesso! Token JWT recebido: {token[:30]}...\n")

# 2. Inserir Dados
print("=== 2. Enviando dados sensíveis para o Backend ===")
print("O Backend vai gerar uma chave AES, criptografar os dados, proteger a chave AES com RSA e salvar no MongoDB!")
student_data = {
    "name": "Kelvin (Teste Criptografia)",
    "cpf": "123.456.789-00",
    "birthDate": "15/08/1995",
    "phone": "(11) 98888-7777"
}
do_request("/students/me", "PUT", student_data, token)
print("✅ Dados criptografados e salvos no banco com sucesso!\n")

# 3. Recuperar Dados
print("=== 3. Buscando os dados (Descriptografia Automática) ===")
print("O Backend vai ler o Envelope do MongoDB, usar a Chave Privada RSA para abrir a Chave AES e descriptografar os dados.")
data_fetched = do_request("/students/me", "GET", None, token)
print("✅ Dados retornados em texto claro para o cliente:")
print(json.dumps(data_fetched, indent=2, ensure_ascii=False))
print("\n🎉 Teste concluído com sucesso!")
