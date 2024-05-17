package com.example.student;

import com.example.student.model.Student;
import com.netflix.discovery.EurekaClient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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

    @GetMapping("")
    public ModelAndView home(Model model) {
        String authServiceUrl = eurekaClient.getNextServerFromEureka("AUTHENTICATION", false).getHomePageUrl();
        String id = CoursesController.getSession();
        System.out.println("ID IS "+id);
        Student student = restTemplate.getForObject(authServiceUrl + "/api/students/" + id, Student.class);
        model.addAttribute("student", student);
        return new ModelAndView("home.html");
    }

    @GetMapping("/enrollments")
    public ModelAndView enrollments(@RequestParam("studentid") String studentid){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("enrollments.html");
        modelAndView.addObject("studentid", studentid);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("studentid") String studentid){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search.html");
        modelAndView.addObject("studentid", studentid);
        return modelAndView;
    }

}
