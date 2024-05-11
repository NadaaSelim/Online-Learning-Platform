package com.example.CourseManagement;

import lombok.Data;

@Data
public class Review {
    Student student;
    String comment;
    int rating;
}
