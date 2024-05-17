package com.example.authentication.instructor;


import com.example.authentication.student.StudentRepository;
import com.netflix.discovery.EurekaClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;
    private final EurekaClient eurekaClient;

    private final RestTemplate restTemplate;

    public InstructorController(InstructorRepository instructorRepository, StudentRepository studentRepository, EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
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
        String instructorServiceUrl = eurekaClient.getNextServerFromEureka("instructor-service", false).getHomePageUrl();

        restTemplate.getForObject(instructorServiceUrl+"/api/courses/set/"+instructor.getId()
                ,String.class);

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
            String instructorServiceUrl = eurekaClient.getNextServerFromEureka("instructor-service", false).getHomePageUrl();

            restTemplate.getForObject(instructorServiceUrl+"/api/courses/set/"+instructor1.getId()
                    ,String.class);

            return 1;
            //return "Login successful";
        }
        return 0;// return "Login failed";
    }

    @GetMapping("/{id}")
    public Instructor getInstructor(@PathVariable("id") String id){
        return instructorRepository.findInstructorById(id);
    }

}
