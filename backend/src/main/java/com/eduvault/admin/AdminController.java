package com.eduvault.admin;

import com.eduvault.student.StudentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/students")
    public List<StudentResponse> getAllStudents() {
        return adminService.listAllStudents();
    }

    @GetMapping("/students/{id}")
    public StudentResponse getStudentById(@PathVariable String id) {
        return adminService.getStudentById(id);
    }
}
