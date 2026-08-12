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

    @Test
    public void testRsaEncryptionOfAesKey() throws Exception {
        CryptoService cryptoService = new CryptoService();

        // 1. Gera par de chaves RSA
        java.security.KeyPair rsaKeyPair = cryptoService.generateRsaKeyPair();
        assertNotNull(rsaKeyPair.getPublic());
        assertNotNull(rsaKeyPair.getPrivate());

        // 2. Gera chave AES
        SecretKey originalAesKey = cryptoService.generateAesKey();
        byte[] originalAesKeyBytes = originalAesKey.getEncoded();

        // 3. Tranca a chave AES com RSA Público
        byte[] encryptedAesKey = cryptoService.encryptRSA(originalAesKey, rsaKeyPair.getPublic());
        assertNotNull(encryptedAesKey);
        assertNotEquals(Base64.getEncoder().encodeToString(originalAesKeyBytes), Base64.getEncoder().encodeToString(encryptedAesKey));

        // 4. Destranca a chave AES com RSA Privado
        SecretKey decryptedAesKey = cryptoService.decryptRSA(encryptedAesKey, rsaKeyPair.getPrivate());
        byte[] decryptedAesKeyBytes = decryptedAesKey.getEncoded();

        // Verifica se a chave AES extraída é exatamente igual à original
        assertEquals(
                Base64.getEncoder().encodeToString(originalAesKeyBytes),
                Base64.getEncoder().encodeToString(decryptedAesKeyBytes)
        );
    }
}
