package com.example.instructor;

import com.example.instructor.models.Course;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CourseService courseService;
    static HttpSession session;
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

    @GetMapping("/set/{atrVal}")
    public void setSession(@PathVariable("atrVal") String atrVal, HttpServletRequest request ){
        String atrKey = "sessionID";
        System.out.println("Recieved "+atrKey+atrVal);
        session = request.getSession();
        session.setAttribute(atrKey, atrVal);
        System.out.println("getAttribute() "+session.getAttribute(atrKey));
        System.out.println(Arrays.toString(request.getCookies()));
        //return new ModelAndView("home");
        //return "redirect:/home.html";
    }
    public static String getSession(){
        return (String) session.getAttribute("sessionID");
    }

    @GetMapping("/name")
    public List<Course> findCourseByName(@RequestParam("name") String name){
        return courseService.findCourseByName(name);
    }

    @GetMapping("/category")
    public List<Course> findCourseByCategory(@RequestParam("category") String category) {
            return courseService.findCategory(category);
    }

    @GetMapping("/sorted")
    public List<Course> sortByRating(){
        return  courseService.getCoursesByRating();

    }

    // accept
    @PutMapping("/accept/{courseid}/{studentid}/{instrId}")
    public String accept(@PathVariable("courseid") String courseid, @PathVariable("studentid") String studentid, @PathVariable("instrId") String instrId){
        return courseService.accept(courseid,studentid,instrId);
    }
    // reject
    @PutMapping("/reject/{courseid}/{studentid}/{instrId}")
    public String reject(@PathVariable("courseid") String courseid, @PathVariable("studentid") String studentid, @PathVariable("instrId") String instrId){
        return courseService.reject(courseid,studentid,instrId);
    }





}
