package com.example.student.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Data
@ToString
@Getter
@Setter
public class Enrollment {

    public String CourseId;
    public String StudentId;
}
