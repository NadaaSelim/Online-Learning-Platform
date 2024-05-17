package com.example.demo;


import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@ManagedBean
public class RequesterService {

    @EJB
    private Requester requester;

    public List<Instructor> getInstructors() {
        return requester.fetchDataFromUrl("http://localhost:8080/api/instructors/all", Instructor.class);
    }

    public List<Student> getStudents() {
        return requester.fetchDataFromUrl("http://localhost:8080/api/students/all", Student.class);
    }
}
