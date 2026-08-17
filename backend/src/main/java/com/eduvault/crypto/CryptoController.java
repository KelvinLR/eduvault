package com.eduvault.crypto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.core.ObjectReadContext;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("api/crypto")
public class CryptoController {
    private final CryptoService service;

    public CryptoController(CryptoService service) { this.service = service; }

    @GetMapping("/public-key")
    public Map<String, String> getPublicKey() {
        byte[] publicKey = service.getPublicKey().getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(publicKey);

        return Map.of("publicKey", encodedKey);
    }

}
