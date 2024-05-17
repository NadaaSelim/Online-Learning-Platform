package com.example.student;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class StudentApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
    @GetMapping("/enrollments")
    public ModelAndView enrollments(@RequestParam("studentid") String studentid){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("enrollments.html");
        modelAndView.addObject("studentid", studentid);
        return modelAndView;
    }


}
