package com.example.CourseManagement;

import lombok.Data;

@Data
public class Student {

    private String id;
    private Review review;
    private Status status=Status.PENDING;
    private float grade=0;

}