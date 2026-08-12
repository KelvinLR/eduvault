package com.eduvault.crypto;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CryptoService {

    private static final String AES_ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_IV_LENGTH = 12; // Recomendado para GCM
    private static final int GCM_TAG_LENGTH = 128; // em bits

    private final SecureRandom secureRandom = new SecureRandom();

    // 1. Gera uma Chave Simétrica (AES-256)
    public SecretKey generateAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE, secureRandom);
        return keyGenerator.generateKey();
    }

    // 2. Gera um Vetor de Inicialização (IV) aleatório
    public byte[] generateIv() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        secureRandom.nextBytes(iv);
        return iv;
    }

    // 3. Criptografa o dado usando AES-256-GCM
    public byte[] encryptAES(String plaintext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
        return cipher.doFinal(plaintext.getBytes());
    }

    // 4. Descriptografa o dado AES-256-GCM
    public String decryptAES(byte[] ciphertext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
        byte[] decryptedText = cipher.doFinal(ciphertext);
        
        return new String(decryptedText);
    }

    // --- RSA Methods ---

    // 5. Gera um par de chaves RSA (Pública e Privada)
    public java.security.KeyPair generateRsaKeyPair() throws NoSuchAlgorithmException {
        java.security.KeyPairGenerator keyGen = java.security.KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Tamanho seguro
        return keyGen.generateKeyPair();
    }

    // 6. Criptografa a Chave AES usando a Chave Pública RSA
    public byte[] encryptRSA(SecretKey aesKey, java.security.PublicKey rsaPublicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        // O SecretKey.getEncoded() retorna os bytes brutos da chave AES (32 bytes para AES-256)
        return cipher.doFinal(aesKey.getEncoded());
    }

    // 7. Descriptografa a Chave AES usando a Chave Privada RSA
    public SecretKey decryptRSA(byte[] encryptedAesKey, java.security.PrivateKey rsaPrivateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        byte[] decryptedKeyBytes = cipher.doFinal(encryptedAesKey);
        
        // Reconstrói a chave AES a partir dos bytes originais
        return new javax.crypto.spec.SecretKeySpec(decryptedKeyBytes, "AES");
    }
}
