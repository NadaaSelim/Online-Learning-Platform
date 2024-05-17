package com.example.student;


import com.example.student.model.Student;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamsController {

    private final ExamsService examsService;

    public ExamsController(ExamsService examsService) {
        this.examsService = examsService;
    }

    @GetMapping("/all/{centerId}")
    //Todo implement this method
    public List<Exam> getAllExams(@PathVariable("centerId") String centerId){
        return examsService.getAllExams(centerId);
    }

    @GetMapping("/centers")
    public List<Center> getCenters(){
        return examsService.getCenters();
    }

    @PostMapping("/register/{examId}")
    public boolean register(@PathVariable("examId") String examId, @RequestBody Student student){
        return examsService.register(examId, student);
    }

}
