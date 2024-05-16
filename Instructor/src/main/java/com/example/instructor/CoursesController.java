package com.example.instructor;

import com.example.instructor.models.Course;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")

    public ResponseEntity<Object> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/add")
    public Course addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }



    @GetMapping("/name")
    public List<Course> findCourseByName(@RequestParam("name") String name){
        return courseService.findCourseByName(name);
    }

    @GetMapping("/category")
    public List<Course> findCourseByCategory(@RequestParam("category") String category) {
            return courseService.findCategory(category);
    }

    @GetMapping("/all")
    public List<Course> sortByRating(){
        return  courseService.getCoursesByRating();

    }

    // accept
    // reject





}
