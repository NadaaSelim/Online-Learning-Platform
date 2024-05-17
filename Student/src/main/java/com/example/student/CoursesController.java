package com.example.student;

import com.example.student.model.Course;
import com.example.student.model.Enrollment;
import com.example.student.model.UiCourse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CourseService courseService;
    static HttpSession session;
    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/set/{atrVal}")
    public void setSession( @PathVariable("atrVal") String atrVal,HttpServletRequest request ){
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
    @GetMapping("")
    public List<UiCourse> getAllCourses(Model model) {

        List<UiCourse> uiCourses = courseService.getAllCourses().stream().map(course -> {
            UiCourse uiCourse = new UiCourse();
            uiCourse.setId(course.getId());
            uiCourse.setName(course.getName());
            uiCourse.setInstructor(course.getInstructor().getInstructorName());
            uiCourse.setDuration(course.getDuration());
            uiCourse.setCapacity(course.getCapacity());
            uiCourse.setCategory(course.getCategory());
            return uiCourse;
        }).toList();

        model.addAttribute("courses", uiCourses);
        return uiCourses;
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
