package com.example.student.model;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class UiCourse {

    private String id;
    private String name;
    private String instructor;
    private int duration;       //duration in HOURS
    private int capacity;
    private  Category category;
}
