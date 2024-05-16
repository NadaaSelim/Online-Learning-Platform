package com.example.student;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class StudentApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
//    @GetMapping("")
//    public ModelAndView home(Model mode){
//        String authServiceUrl = eurekaClient.getNextServerFromEureka("authentication", false).getHomePageUrl();
//        String id = (String) request.getSession().getAttribute("sessionID");
//        Student student = restTemplate.getForObject(authServiceUrl+"/api/students/"+ id,Student.class);
//        model.addAttribute("student", student);
//         return new ModelAndView("home.html");
//    }

}
