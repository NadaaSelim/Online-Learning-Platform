package com.example.student.model;

import lombok.Data;

@Data
public class Student {

    private String id;
    private String name;
    private Review review;

}