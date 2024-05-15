package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {
    private String id;
    private String name;
    private String email;
    private String password;
    private String affiliation;
    private String bio;
    private Review review;
    List<Student> students;
    public Review getReview(){return review;}
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getBio() {
        return bio;
    }
    public List<Student> getStudents(){return students;}
}
