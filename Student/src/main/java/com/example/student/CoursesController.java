package com.example.student;

import com.example.student.model.Course;
import com.example.student.model.Enrollment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CourseService courseService;
    private final ObjectMapper objectMapper;

    public CoursesController(CourseService courseService, ObjectMapper objectMapper) {
        this.courseService = courseService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping(value ="/enroll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void enrollCourse(@RequestBody Enrollment enrollment) throws JsonProcessingException {
        courseService.enrollCourse(enrollment);
    }
}
