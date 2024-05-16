package org.example.TestRep;

import org.example.TestRep.model.Center;
import org.example.TestRep.model.TestRep;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Center")
public class TestRepController {

    private final TestRepRepository testRepRepository;
    private final CenterRepository centerRepository;
    private final ExamRepository examRepository;

    public TestRepController(TestRepRepository testRepRepository, CenterRepository centerRepository, ExamRepository examRepository){
        this.testRepRepository = testRepRepository;
        this.centerRepository = centerRepository;
        this.examRepository = examRepository;
    }

    @PutMapping("/updateInfo/{id}")
    public TestRep updateCenterInfo(@PathVariable  String id, @RequestBody Center center){
        Optional<TestRep> OptionaltestRep =  testRepRepository.findById(id);
        if(OptionaltestRep.isPresent())
        {

            TestRep testrep = OptionaltestRep.get();
            testrep.setCenter(center);
            return  testRepRepository.save(testrep);
        }
        else{
            System.out.println("User not found with id: " + id);
        }

        return null;
    }



}
