package org.example.TestRep;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController("")
public class UIController {
    @GetMapping("")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("home.html");
        return mv;
    }
    @GetMapping("/add")
    public ModelAndView addExam(){
        ModelAndView mv = new ModelAndView("addExam.html");
        String id = "id";
        mv.addObject("repId", id);
        return mv;
    }

    @GetMapping("/update")
    public ModelAndView updateInfo(){
        ModelAndView mv = new ModelAndView("updateInfo.html");
        return mv;
    }
    @GetMapping("/view")
    public ModelAndView viewExam(){
        ModelAndView mv = new ModelAndView("viewExam.html");
        return mv;
    }
}