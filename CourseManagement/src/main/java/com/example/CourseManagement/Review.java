package com.example.CourseManagement;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class Review {

    String comment;
    int rating;
}
