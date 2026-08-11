package com.eduvault.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// @Service diz q aqui tem regra de negocios
@Service
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public StudentResponse getMyData() {
        List<StudentDocument> students = repository.findAll();
        if (students.isEmpty()) {
            return new StudentResponse("Nenhum aluno cadastrado", "", "", "");
        }
        StudentDocument doc = students.get(0);
        return new StudentResponse(doc.getName(), doc.getCpf(), doc.getBirthDate(), doc.getPhone());
    }

    public void updateMyData(StudentResponse studentResponse) {
        // Mais tarde, é AQUI no Service que faremos a criptografia antes de salvar!
        StudentDocument document = new StudentDocument(
                studentResponse.name(),
                studentResponse.cpf(),
                studentResponse.birthDate(),
                studentResponse.phone()
        );
        repository.save(document);
    }
}
