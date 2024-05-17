package com.example.student;

import com.example.student.model.Student;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data

public class Exam {

    private String id;
    private String name;
    private int duration;

    private List<LocalDate> dates= new ArrayList<>();
    private List<Student> students= new ArrayList<>();
    private String cid;


}
