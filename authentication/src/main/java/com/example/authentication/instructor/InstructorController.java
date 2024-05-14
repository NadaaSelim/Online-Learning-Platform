package com.example.authentication.instructor;


import com.example.authentication.student.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    public InstructorController(InstructorRepository instructorRepository, StudentRepository studentRepository) {
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/all")
    List<Instructor> findAll(){
        return instructorRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id){
        instructorRepository.deleteById(id);
    }
    @PostMapping("/register")
    public String registerInstructor(@ModelAttribute Instructor instructor) {
        if(studentRepository.findStudentByEmail(instructor.getEmail()).isPresent())
            return "Email already exists as a Student";
        try {
            instructorRepository.insert(instructor);
        }
        catch (Exception e) {
            return "Instructor already exists";
        }
        return "Instructor registered";
    }

    @PostMapping("/login")
    public int loginInstructor(@ModelAttribute Instructor instructor) {
        Instructor instructor1 = instructorRepository.findInstructorByEmail(instructor.getEmail()).orElse(null);
        if (instructor1 == null) {
            return -1;
            //return "Instructor not found, Please create an account";
        }
        if (instructor1.getPassword().equals(instructor.getPassword())) {
            return 1;
            //return "Login successful";
        }
        return 0;// return "Login failed";
    }

}
