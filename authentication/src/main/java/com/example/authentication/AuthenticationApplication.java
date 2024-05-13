package com.example.authentication;


import com.example.authentication.instructor.Instructor;
import com.example.authentication.student.Student;
import com.example.authentication.student.StudentController;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@SpringBootApplication
@RestController
public class AuthenticationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }
    @GetMapping("")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }
    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register.html");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

//    @GetMapping("/verify")
//    public ModelAndView verify(@ModelAttribute Student student){
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("verify.html");
//        return modelAndView;
//    }
//    @GetMapping("/verify")
//    public ModelAndView verify(@ModelAttribute Instructor instructor){
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("verify.html");
//        return modelAndView;
//    }

}
