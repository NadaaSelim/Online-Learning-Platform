package com.example.CourseManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private final CourseRepository courserepo;
    public CourseController(CourseRepository courserepo) {
        this.courserepo = courserepo;
    }
    @PostMapping("/add")
    public Course addCourse(@RequestBody Course course) {
        return courserepo.save(course);
    }

    @GetMapping("/{id}")      //id?=[id]
    public Optional<Course> getCourseById(@PathVariable String id) {
       return courserepo.findById(id);
    }

    @GetMapping("/name")
    public List<Course> findCourseByName( @RequestParam String coursename) {
        return courserepo.findCoursesByNameContainingIgnoreCase(coursename);
    }

    @GetMapping("/category")
    public List<Course> findCourseByCategory(@RequestParam String category) {
        return courserepo.findByCategory(Category.valueOf(category));
    }

    @GetMapping("/all")
    public List<Course> getCoursesByRating() {
        return courserepo.findAllByOrderByAverageRatingDesc();
    }
}
