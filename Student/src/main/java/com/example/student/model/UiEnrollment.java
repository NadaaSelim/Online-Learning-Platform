package com.example.student.model;

import lombok.Data;

@Data
public class UiEnrollment {

    private String name;
    private String instructor;
    private Category category;
    private Status status;
}
