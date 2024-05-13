package com.example.student;

import com.example.student.model.Course;
import com.example.student.model.Enrollment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CourseService courseService;
    private final ObjectMapper objectMapper;
    HttpSession session;
    public CoursesController(CourseService courseService, ObjectMapper objectMapper) {
        this.courseService = courseService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/set/{atrKey}/{atrVal}")
    public void setSession(@PathVariable("atrKey") String atrKey, @PathVariable("atrVal") String atrVal,HttpServletRequest request ){
        System.out.println("Recieved "+atrKey+atrVal);
         session = request.getSession();
        session.setAttribute(atrKey, atrVal);
        System.out.println("getAttribute() "+session.getAttribute(atrKey));
        System.out.println(Arrays.toString(request.getCookies()));
    }
    @GetMapping("")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping(value ="/enroll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void enrollCourse(@RequestBody Enrollment enrollment) throws JsonProcessingException {
        courseService.enrollCourse(enrollment);
    }

    @PostMapping("/drop")
    public void dropCourse(@RequestBody Enrollment enrollment) throws JsonProcessingException {
        courseService.dropCourse(enrollment);
    }
}
