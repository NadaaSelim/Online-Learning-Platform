package com.example.student;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamsController {

    private final ExamsService examsService;

    public ExamsController(ExamsService examsService) {
        this.examsService = examsService;
    }

    @GetMapping("/all")
    //Todo implement this method
    public String getAllExams(){
        return "All Exams";
    }

    @GetMapping("/centers")
    public List<Center> getCenters(){
        return examsService.getCenters();
    }

}
