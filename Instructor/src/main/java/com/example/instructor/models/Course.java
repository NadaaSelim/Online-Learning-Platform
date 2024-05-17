package com.example.instructor.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


enum Category{ HU, TECH , SCI}

@Data
// a name, duration, category, rating, capacity,
//number of enrolled students, and list of reviews.
public class Course {

    private String id;
    private String name;
    private Instructor instructor;
    private int duration;       //duration in HOURS
    private int capacity;
    private  Category category;
    private List<Student> students = new ArrayList<>();


}
