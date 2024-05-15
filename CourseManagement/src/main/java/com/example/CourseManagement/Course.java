package com.example.CourseManagement;

import java.util.ArrayList;
import java.util.List;

import com.example.CourseManagement.model.Instructor;
import com.example.CourseManagement.model.Student;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


enum Category{ HU, TECH , SCI}

@Document
@Data
// a name, duration, category, rating, capacity,
//number of enrolled students, and list of reviews.

public class Course {
    @Id
    private String id;
    private String name;
    private Instructor instructor;
    private int duration;       //duration in HOURS
    private int capacity;
    private  Category category;
    private List<Student> students = new ArrayList<>();
    private boolean published=false;
}
