package com.example.instructor;

import com.example.instructor.models.Course;
import com.example.instructor.models.Instructor;
import com.netflix.discovery.EurekaClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController("")
public class UIController {
    private final EurekaClient eurekaClient;
    @LoadBalanced // Enables client-side load balancing
    private final RestTemplate restTemplate;


    public UIController(EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
    }
    @GetMapping("/search")
    public ModelAndView search(){
        System.out.println("In search");
        return new ModelAndView("search.html");
    }
    @GetMapping("/enrollements")
    public ModelAndView enrollments(@RequestParam("instructorid") String instructorid) {
        ModelAndView modelAndView = new ModelAndView("enrollments.html");
        modelAndView.addObject("instructorid", instructorid);
        return modelAndView;
    }
    @GetMapping("")
    public ModelAndView index() {
        String authServiceUrl = eurekaClient.getNextServerFromEureka("AUTHENTICATION", false).getHomePageUrl();
        String id = CoursesController.getSession();
        System.out.println("ID IS "+id);
        // instructor = restTemplate.getForObject(authServiceUrl + "/api/instructors/" + id, Instructor.class);
        ModelAndView modelAndView = new ModelAndView("index.html");
        modelAndView.addObject("instrId", id);
        //System.out.println(instructor.getId()+instructor.getInstructorName());

        return modelAndView;
    }
    @GetMapping("/add")
    public ModelAndView addCourse() {
        String authServiceUrl = eurekaClient.getNextServerFromEureka("AUTHENTICATION", false).getHomePageUrl();
        String id = CoursesController.getSession();
        System.out.println("ID IS "+id);
        Instructor instructor = restTemplate.getForObject(authServiceUrl + "/api/instructors/" + id, Instructor.class);
        ModelAndView modelAndView = new ModelAndView("addCourse.html");
        modelAndView.addObject("id", instructor.getId());
        System.out.println(instructor.getId()+instructor.getInstructorName());
        modelAndView.addObject("instructorName", "");

        return modelAndView;
        //return "index";
    }

}
