package org.example.TestRep;

import org.example.TestRep.model.Center;
import org.example.TestRep.model.Exam;
import org.example.TestRep.model.TestRep;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add")
    public ResponseEntity<String> maketestrep(@RequestBody TestRep testRep){
        try {
            // Insert the TestRep document
            testRepRepository.insert(testRep);
            return ResponseEntity.status(HttpStatus.CREATED).body("TestRep document created successfully.");
        } catch (Exception e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create TestRep document: " + e.getMessage());
        }
    }

    @PutMapping("/updateInfo/{id}")
    public ResponseEntity<TestRep> updateCenterInfo(@PathVariable("id") String id, @RequestBody Center center) {
        // Find the TestRep document by ID
        Optional<TestRep> optionalTestRep = testRepRepository.findById(id);
        Center c;

        if (optionalTestRep.isPresent()) {

            TestRep testRep = optionalTestRep.get();
            String Cid = testRep.getCenter().getId();

            if(centerRepository.findById(Cid).isPresent()){
                Optional<Center> optionalCenter =centerRepository.findById(Cid);
                c = optionalCenter.get();
                c.setAddress(center.getAddress());
                c.setLocation(center.getLocation());
                c.setBio(center.getBio());
                c.setEmail(center.getEmail());
                centerRepository.save(c);
            }else{
                c = centerRepository.insert(center);
            }

            // Update the Center information
            testRep.setCenter(c);

            // Save the updated TestRep document
            TestRep updatedTestRep = testRepRepository.save(testRep);

            return ResponseEntity.ok(updatedTestRep);
        } else {
            // Document with the specified ID not found
            return ResponseEntity.notFound().build();
        }
    }

    //create exams
    @PostMapping("/addExam/{id}")
    public ResponseEntity<String> createExam (@PathVariable("id") String testRepId , @RequestBody Exam exam){
        try {
            // Insert the TestRep document
            examRepository.insert(exam);
            return ResponseEntity.status(HttpStatus.CREATED).body("Exam document created successfully.");
        } catch (Exception e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Exam document: " + e.getMessage());
        }
    }

    //set grades for students
    //@PutMapping

    //view exams

    // view students grades of exams



}
