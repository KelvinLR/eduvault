package com.eduvault.student;

import com.eduvault.crypto.EncryptedPayload;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class StudentDocument {

    @Id
    private String id;
    
    private String userId;

    private EncryptedPayload encryptedPayload;

    public StudentDocument() {
    }

    public StudentDocument(String userId, EncryptedPayload encryptedPayload) {
        this.userId = userId;
        this.encryptedPayload = encryptedPayload;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public EncryptedPayload getEncryptedPayload() {
        return encryptedPayload;
    }

    public void setEncryptedPayload(EncryptedPayload encryptedPayload) {
        this.encryptedPayload = encryptedPayload;
    }
}
