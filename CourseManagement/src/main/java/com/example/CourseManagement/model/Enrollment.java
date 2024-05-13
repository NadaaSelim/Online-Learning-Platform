package com.example.CourseManagement.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Getter
@Setter
public class Enrollment {

    public String courseid;
    public String studentid;
}
