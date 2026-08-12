package com.eduvault.admin;

import com.eduvault.student.StudentDocument;
import com.eduvault.student.StudentRepository;
import com.eduvault.student.StudentResponse;
import com.eduvault.student.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    public AdminService(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    public List<StudentResponse> listAllStudents() {
        return studentRepository.findAll().stream()
                .filter(doc -> doc.getEncryptedPayload() != null)
                .map(doc -> studentService.decryptPayload(doc.getEncryptedPayload()))
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentById(String id) {
        StudentDocument doc = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        
        if (doc.getEncryptedPayload() == null) {
            return new StudentResponse("Nenhum dado sensível cadastrado", "", "", "");
        }
        return studentService.decryptPayload(doc.getEncryptedPayload());
    }
}
