package org.example.TestRep;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.TestRep.model.Center;
import org.example.TestRep.model.Exam;
import org.example.TestRep.model.Student;
import org.example.TestRep.model.TestRep;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Center")
public class TestRepController {

    private final TestRepRepository testRepRepository;
    private final CenterRepository centerRepository;
    private final ExamRepository examRepository;
    static HttpSession session;

    public TestRepController(TestRepRepository testRepRepository, CenterRepository centerRepository, ExamRepository examRepository){
        this.testRepRepository = testRepRepository;
        this.centerRepository = centerRepository;
        this.examRepository = examRepository;
    }
    @GetMapping("/set/{atrVal}")
    public void setSession(@PathVariable("atrVal") String atrVal, HttpServletRequest request ){
        String atrKey = "sessionID";
        System.out.println("Recieved "+atrKey+atrVal);
        session = request.getSession();
        session.setAttribute(atrKey, atrVal);
        System.out.println("getAttribute() "+session.getAttribute(atrKey));
        System.out.println(Arrays.toString(request.getCookies()));
    }
    public static String getSession(){
        return (String) session.getAttribute("sessionID");
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


            if(testRep.getCenter() != null){
                String Cid = testRep.getCenter().getId();
                Optional<Center> optionalCenter =centerRepository.findById(Cid);
                c = optionalCenter.get();
                c.setAddress(center.getAddress());
                c.setLocation(center.getLocation());
                c.setBio(center.getBio());
                c.setEmail(center.getEmail());
                centerRepository.save(c);
            }
            else{
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

            Optional<TestRep> optionalTestRep = testRepRepository.findById(testRepId);
            if (optionalTestRep.isPresent()) {


                TestRep testRep = optionalTestRep.get();
                String cid = testRep.getCenter().getId();
                exam.setCid(cid);
                // validate the dates are not duplicates
                List<LocalDate> dates =exam.getDates();
                Set<LocalDate> uniqueDates = new HashSet<>(dates);
                if(uniqueDates.size() != dates.size()){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicated dates");

                }
                examRepository.save(exam);
                return ResponseEntity.status(HttpStatus.CREATED).body("Exam document created successfully.");


            }
            else {
                return ResponseEntity.notFound().build();
            }



        } catch (Exception e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Exam document: " + e.getMessage());
        }
    }

    //set grades for students
    @PutMapping("/setGrade/{testRepId}/{examId}/{studId}")
    public ResponseEntity<String> setGrade(@PathVariable("testRepId") String testRepId,@PathVariable("examId") String examId, @PathVariable("studId")String studId,@RequestBody Integer grade ){

        try {

            Optional<TestRep> optionalTestRep = testRepRepository.findById(testRepId);
            if (optionalTestRep.isPresent()) {


                TestRep testRep = optionalTestRep.get();
                String cid = testRep.getCenter().getId();

                Optional<Exam> optionalExam = examRepository.findById(examId);
                if(optionalExam.isPresent()){
                    Exam exam = optionalExam.get();
                    if(exam.getCid().equals(cid)){
                        Optional<Student> optionalStudent = exam.getStudents().stream()
                                .filter(student -> student.getId().equals(studId))
                                .findFirst();
                        if (optionalStudent.isPresent()) {
                            Student student = optionalStudent.get();
                            student.setGrade(grade);
                            examRepository.save(exam);
                           return ResponseEntity.status(HttpStatus.CREATED).body("grade added successfully.");

                        }
                        else{
                            return ResponseEntity.notFound().build();
                        }


                    }else {
                        return ResponseEntity.notFound().build();
                    }
                }
                else{
                    return ResponseEntity.notFound().build();


                }

            }
            else {
                return ResponseEntity.notFound().build();
            }



        } catch (Exception e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Exam document: " + e.getMessage());
        }

    }

    //view exams
    @GetMapping("/exams/{testRepId}")
    public List<Exam> getExams(@PathVariable("testRepId") String testRepId ){
        Optional<TestRep> optionalTestRep = testRepRepository.findById(testRepId);
        if(optionalTestRep.isPresent()){
            TestRep testRep = optionalTestRep.get();
            List<Exam> exams = examRepository.findByCid(testRep.getCenter().getId());
            return exams;


        }
        return null;
}
    // view students grades of exams
    @GetMapping("/grades/{examId}")
    public List<Integer> getGrades(@PathVariable("examId") String examId){

            Optional<Exam> optionalExam = examRepository.findById(examId);
            if (optionalExam.isPresent()) {
                Exam exam = optionalExam.get();
                List<Integer> grades = exam.getStudents().stream()
                        .map(Student::getGrade)
                        .collect(Collectors.toList());

                return grades;
            } else {
                return null;
            }
        }

    }

    // testcenters




