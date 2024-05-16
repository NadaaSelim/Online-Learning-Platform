package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseWithReview {
    public Course getCourse() {
        return course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Course course;
    public List<Student> students;
}
