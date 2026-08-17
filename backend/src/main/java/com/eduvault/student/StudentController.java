package com.eduvault.student;

import com.eduvault.crypto.EncryptedPayload;
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
    public String editStudentInfo(@RequestBody EncryptedPayload encryptedPayload) {
        // O Controller só repassa a chamada
        service.updateMyData(encryptedPayload);
        return "Data successfully updated in MongoDB via Service";
    }
}
