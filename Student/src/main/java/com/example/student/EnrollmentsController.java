package com.example.student;


import com.example.student.model.Course;
import com.example.student.model.UiCourse;
import com.example.student.model.UiEnrollment;
import jakarta.ws.rs.PathParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentsController {

    private final EnrollmentService enrollmentService;

    public EnrollmentsController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/all")
    public List<UiEnrollment> getAllEnrollments(@RequestParam("studentid") String studentid, Model model){
        List<UiEnrollment> uiEnrollments = enrollmentService.getAllEnrollments(studentid);
        model.addAttribute("enrollments", uiEnrollments);
        return uiEnrollments;

    }
}
