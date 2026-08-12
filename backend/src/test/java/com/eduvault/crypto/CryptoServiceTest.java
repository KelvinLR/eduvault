package com.eduvault.crypto;

import org.junit.jupiter.api.Test;
import javax.crypto.SecretKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CryptoServiceTest {

    @Test
    public void testAesEncryptionAndDecryption() throws Exception {
        CryptoService cryptoService = new CryptoService();
        
        // Dado original sensível
        String originalData = "{\"cpf\": \"111.222.333-44\", \"nota\": \"10.0\"}";
        
        // 1. Gera a chave AES e o IV
        SecretKey aesKey = cryptoService.generateAesKey();
        byte[] iv = cryptoService.generateIv();
        
        assertNotNull(aesKey);
        assertNotNull(iv);
        assertEquals(12, iv.length);
        
        // 2. Criptografa
        byte[] ciphertext = cryptoService.encryptAES(originalData, aesKey, iv);
        String ciphertextBase64 = Base64.getEncoder().encodeToString(ciphertext);
        
        // Verifica se o texto cifrado é diferente do original
        assertNotEquals(originalData, ciphertextBase64);
        
        // 3. Descriptografa
        String decryptedData = cryptoService.decryptAES(ciphertext, aesKey, iv);
        
        // Verifica se o dado descriptografado é igual ao original
        assertEquals(originalData, decryptedData);
    }
}
