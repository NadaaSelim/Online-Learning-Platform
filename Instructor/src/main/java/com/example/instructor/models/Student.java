package com.example.instructor.models;

import lombok.Data;

@Data
public class Student {

    private String id;
    private String name;
    private Review review;

}