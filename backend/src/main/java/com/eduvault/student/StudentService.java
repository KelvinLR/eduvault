package com.eduvault.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.eduvault.security.CustomUserDetails;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
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
        // vai verificar ae xistencia dos dados do usuario
        String userId = getAuthenticatedUserId();
        Optional<StudentDocument> optDoc = repository.findByUserId(userId);
        
        if (optDoc.isEmpty()) {
            return new StudentResponse("Nenhum dado sensível cadastrado", "", "", "");
        }
        
        StudentDocument doc = optDoc.get();
        return new StudentResponse(doc.getName(), doc.getCpf(), doc.getBirthDate(), doc.getPhone());
    }

    public void updateMyData(StudentResponse studentResponse) {
        String userId = getAuthenticatedUserId();
        StudentDocument document = repository.findByUserId(userId).orElse(new StudentDocument());
        
        document.setUserId(userId);
        document.setName(studentResponse.name());
        document.setCpf(studentResponse.cpf());
        document.setBirthDate(studentResponse.birthDate());
        document.setPhone(studentResponse.phone());
        
        // Mais tarde, é AQUI no Service que faremos a criptografia antes de salvar!
        repository.save(document);
    }
}
