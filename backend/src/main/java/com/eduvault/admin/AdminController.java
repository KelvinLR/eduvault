package com.eduvault.admin;

import com.eduvault.student.StudentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/students")
    public ArrayList<StudentResponse> getAllStudents() {
        StudentResponse s1 = new StudentResponse("Kelvin", "000.111.222-33", "01/01/2000", "(11) 22222-3333");
        StudentResponse s2 = new StudentResponse("Leo", "000.111.222-33", "01/01/2000", "(11) 22222-3333");
        StudentResponse s3 = new StudentResponse("Vini", "000.111.222-33", "01/01/2000", "(11) 22222-3333");
        StudentResponse s4 = new StudentResponse("Marcelo", "000.111.222-33", "01/01/2000", "(11) 22222-3333");

        ArrayList<StudentResponse> students = new ArrayList<>(
                List.of(s1, s2, s3, s4)
        );

        return students;
    }

    @GetMapping("/students/{id}")
    public StudentResponse getStudentById(@PathVariable int id) {
        return new StudentResponse("Lucas", "000.111.222-33", "01/01/2000", "(11) 22222-3333");
    }

}
