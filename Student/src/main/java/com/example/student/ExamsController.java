package com.example.student;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exams")
public class ExamsController {

    @GetMapping("/all")
    //Todo implement this method
    public String getAllExams(){
        return "All Exams";
    }

}
