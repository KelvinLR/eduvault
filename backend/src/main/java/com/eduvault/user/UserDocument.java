package com.eduvault.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

// Collection "users" vai guardar apenas dados de login (credenciais).
// NUNCA misturamos dados sensíveis (cpf, telefone) com a tabela de usuários.
@Document(collection = "users")
public class UserDocument {

    @Id
    private String id;
    
    private String username;
    
    // Na fase 5, salvaremos isso criptografado (BCrypt), nunca em texto puro!
    private String passwordHash;
    
    // Role define se o usuário é ADMIN ou STUDENT
    private Role role;
    
    private Instant createdAt;

    public UserDocument() {
    }

    public UserDocument(String username, String passwordHash, Role role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
