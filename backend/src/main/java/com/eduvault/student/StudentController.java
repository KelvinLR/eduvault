package com.eduvault.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    // Agora o Controller conversa com o Service, não mais com o Repository
    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public StudentResponse getStudentInfo() {
        // O Controller só repassa a chamada
        return service.getMyData();
    }

    @PutMapping("/me")
    public String editStudentInfo(@RequestBody StudentResponse studentResponse) {
        // O Controller só repassa a chamada
        service.updateMyData(studentResponse);
        return "Data successfully updated in MongoDB via Service";
    }
}
