package com.example.authentication.student;

import com.example.authentication.instructor.InstructorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public StudentController(StudentRepository studentRepository, InstructorRepository instructorRepository) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    @GetMapping("/all")
    List<Student> findAll(){
        return studentRepository.findAll();
    }


    @PostMapping("/register")
    public String registerStudent(@RequestBody Student student) {
        if(instructorRepository.findInstructorByEmail(student.getEmail()).isPresent())
            return "Email already exists as an Instructor";
        try {
            studentRepository.insert(student);
        }
        catch (Exception e) {
            return "Student already exists";
        }
        return "Student registered";
    }

    @PostMapping("/login")
    public String loginStudent(@RequestBody Student student) {
        Student student1 = studentRepository.findStudentByEmail(student.getEmail()).orElse(null);
        if (student1 == null) {
            return "Student not found, Please create an account";
        }
        if (student1.getPassword().equals(student.getPassword())) {
            return "Login successful";
        }
        return "Login failed";
    }
}
