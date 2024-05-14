package com.example.authentication.student;

import com.example.authentication.instructor.InstructorRepository;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;
import  com.example.authentication.config.RestTemplateConfig;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final EurekaClient eurekaClient;

    private final RestTemplate restTemplate;

    public StudentController(StudentRepository studentRepository, InstructorRepository instructorRepository, EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.eurekaClient = eurekaClient;
       // this.restTemplate = restTemplate;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/all")
    List<Student> findAll(){
        return studentRepository.findAll();
    }


    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student) {
        if(instructorRepository.findInstructorByEmail(student.getEmail()).isPresent())
            return "Email already exists as an Instructor";
        try {
            studentRepository.insert(student);
        }
        catch (Exception e) {
            return "Student already exists";
        }
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("student-service", false).getHomePageUrl();

        restTemplate.getForObject(courseServiceUrl+"/api/courses/set/"+student.getId()
                ,String.class);

        return "Student registered.\n";
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id){
        studentRepository.deleteById(id);
    }
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") String id){
        return studentRepository.findStudentById(id);
    }
    @PostMapping("/login")
    public int loginStudent(@ModelAttribute Student student) {
        Student student1 = studentRepository.findStudentByEmail(student.getEmail()).orElse(null);
        if (student1 == null) {
            //return "Student not found, Please create an account";
            return -1;
        }
        if (student1.getPassword().equals(student.getPassword())) {
            String courseServiceUrl = eurekaClient.getNextServerFromEureka("student-service", false).getHomePageUrl();

            restTemplate.getForObject(courseServiceUrl+"/api/courses/set/"+student1.getId()
                        ,String.class);

            return 1;
            //return "Login successful";
        }
        return 0;
        //return "Login failed";
    }
}
