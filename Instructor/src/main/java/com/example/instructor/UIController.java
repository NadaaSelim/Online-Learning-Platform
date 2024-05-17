package com.example.instructor;

import com.example.instructor.models.Course;
import com.example.instructor.models.Instructor;
import com.netflix.discovery.EurekaClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("")
    public ModelAndView index() {

        return new ModelAndView("home.html");
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
