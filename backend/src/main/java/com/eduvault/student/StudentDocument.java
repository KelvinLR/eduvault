package com.eduvault.student;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// @Document indica que essa classe será mapeada para uma collection no MongoDB.
// O nome da collection será "students".
@Document(collection = "students")
public class StudentDocument {

    @Id
    private String id;
    
    private String userId;

    private String name;
    private String cpf;
    private String birthDate;
    private String phone;

    public StudentDocument() {
    }

    public StudentDocument(String userId, String name, String cpf, String birthDate, String phone) {
        this.userId = userId;
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.phone = phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
