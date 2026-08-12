package com.eduvault.admin;

import com.eduvault.student.StudentDocument;
import com.eduvault.student.StudentRepository;
import com.eduvault.student.StudentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final StudentRepository studentRepository;

    public AdminService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponse> listAllStudents() {
        return studentRepository.findAll().stream()
                .map(doc -> new StudentResponse(doc.getName(), doc.getCpf(), doc.getBirthDate(), doc.getPhone()))
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentById(String id) {
        StudentDocument doc = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        return new StudentResponse(doc.getName(), doc.getCpf(), doc.getBirthDate(), doc.getPhone());
    }
}
