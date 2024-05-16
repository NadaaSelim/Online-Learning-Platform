package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


enum Category{ HU, TECH , SCI}


// a name, duration, category, rating, capacity,
//number of enrolled students, and list of reviews
@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {
    private String id;
    private String name;
    private int duration;       //duration in HOURS
    private int capacity;
    private  Category category;
    private boolean published;
   private List<Student> students;
    private double averageRating;
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public Instructor getInstructor() {
//        return instructor;
//    }

    public int getDuration() {
        return duration;
    }

    public int getCapacity() {
        return capacity;
    }

    public Category getCategory() {
        return category;
    }

    public List<Student> getStudents() {
        return students;
    }

    public boolean isPublished() {
        return published;
    }

    public double getAverageRating() {
        return averageRating;
    }

}
