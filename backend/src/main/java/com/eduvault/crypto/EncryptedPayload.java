package com.eduvault.crypto;

public class EncryptedPayload {

    private String ciphertext;
    private String iv;
    private String encryptedAesKey;

    public EncryptedPayload() {
    }

    public EncryptedPayload(String ciphertext, String iv, String encryptedAesKey) {
        this.ciphertext = ciphertext;
        this.iv = iv;
        this.encryptedAesKey = encryptedAesKey;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getEncryptedAesKey() {
        return encryptedAesKey;
    }

    public void setEncryptedAesKey(String encryptedAesKey) {
        this.encryptedAesKey = encryptedAesKey;
    }
}
