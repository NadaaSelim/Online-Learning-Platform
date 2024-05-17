package com.example.authentication.testRep;

import com.example.authentication.student.Student;
import com.netflix.discovery.EurekaClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/TestRep")
public class TestRepController {

    private final TestRepRepository testRepRepository;

    private final EurekaClient eurekaClient;

    private final RestTemplate restTemplate;

    public TestRepController(TestRepRepository testRepRepository,EurekaClient eurekaClient,RestTemplate restTemplate){
        this.testRepRepository = testRepRepository;
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/all")
    List<TestRep> findAll(){
        return testRepRepository.findAll();
    }

    @GetMapping("/{id}")
    public TestRep getTestRep(@PathVariable("id") String id){
        return testRepRepository.findTestRepById(id);
    }

    @PostMapping("/login")
    public int loginTestRep(@ModelAttribute TestRep testRep) {
        TestRep testRep1 = testRepRepository.findTestRepByEmail(testRep.getEmail()).orElse(null);
        if (testRep1 == null) {
            //return "Student not found, Please create an account";
            return -1;
        }
        if (testRep1.getPassword().equals(testRep.getPassword())) {
            String courseServiceUrl = eurekaClient.getNextServerFromEureka("testrep-service", false).getHomePageUrl();

            restTemplate.getForObject(courseServiceUrl+"/Center/set/"+testRep1.getId()
                    ,String.class);

            return 1;
            //return "Login successful";
        }
        return 0;
        //return "Login failed";
    }


    //login
    //register
    //get

}
