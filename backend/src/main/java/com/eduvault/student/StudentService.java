package com.eduvault.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.eduvault.crypto.CryptoService;
import com.eduvault.crypto.EncryptedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.eduvault.security.CustomUserDetails;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final CryptoService cryptoService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Instanciado diretamente

    @Autowired
    public StudentService(StudentRepository repository, CryptoService cryptoService) {
        this.repository = repository;
        this.cryptoService = cryptoService;
    }

    private String getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Não autenticado");
        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser().getId();
    }

    public StudentResponse getMyData() {
        String userId = getAuthenticatedUserId();
        Optional<StudentDocument> optDoc = repository.findByUserId(userId);
        
        if (optDoc.isEmpty() || optDoc.get().getEncryptedPayload() == null) {
            return new StudentResponse("Nenhum dado sensível cadastrado", "", "", "");
        }
        
        return decryptPayload(optDoc.get().getEncryptedPayload());
    }

    public void updateMyData(StudentResponse studentResponse) {
        String userId = getAuthenticatedUserId();
        StudentDocument document = repository.findByUserId(userId).orElse(new StudentDocument());
        document.setUserId(userId);
        
        document.setEncryptedPayload(encryptPayload(studentResponse));
        
        repository.save(document);
    }

    // --- Métodos Auxiliares de Criptografia ---

    public EncryptedPayload encryptPayload(StudentResponse data) {
        try {
            // 1. Gera chave AES e IV
            SecretKey aesKey = cryptoService.generateAesKey();
            byte[] iv = cryptoService.generateIv();

            // 2. Converte o objeto para String JSON
            String jsonRaw = objectMapper.writeValueAsString(data);

            // 3. Criptografa o JSON
            byte[] ciphertext = cryptoService.encryptAES(jsonRaw, aesKey, iv);

            // 4. Tranca a chave AES com a chave pública RSA do servidor
            byte[] encryptedAesKey = cryptoService.encryptRSA(aesKey, cryptoService.getPublicKey());

            // 5. Retorna o Envelope
            return new EncryptedPayload(
                    Base64.getEncoder().encodeToString(ciphertext),
                    Base64.getEncoder().encodeToString(iv),
                    Base64.getEncoder().encodeToString(encryptedAesKey)
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar dados do estudante", e);
        }
    }

    public StudentResponse decryptPayload(EncryptedPayload payload) {
        try {
            // 1. Decodifica Base64
            byte[] ciphertext = Base64.getDecoder().decode(payload.getCiphertext());
            byte[] iv = Base64.getDecoder().decode(payload.getIv());
            byte[] encryptedAesKey = Base64.getDecoder().decode(payload.getEncryptedAesKey());

            // 2. Destranca a chave AES usando a Chave Privada RSA do servidor
            SecretKey aesKey = cryptoService.decryptRSA(encryptedAesKey, cryptoService.getPrivateKey());

            // 3. Descriptografa o dado
            String jsonRaw = cryptoService.decryptAES(ciphertext, aesKey, iv);

            // 4. Converte de volta para Objeto
            return objectMapper.readValue(jsonRaw, StudentResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar dados do estudante", e);
        }
    }
}
