package com.example.CourseManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class Review {

    String comment;
    int rating;
}
