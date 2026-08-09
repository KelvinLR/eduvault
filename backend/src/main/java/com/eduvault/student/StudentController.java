package com.eduvault.student;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    @GetMapping("/me")
    public StudentResponse getStudentInfo() {
        return new StudentResponse("Kelvin", "000.111.222-33", "01/01/2000", "(11) 22222-3333");
    }
    @PutMapping("/me")
    public String editStudentInfo(@RequestBody StudentResponse studentResponse) {
        return "Data successfully updated";
    }
}
