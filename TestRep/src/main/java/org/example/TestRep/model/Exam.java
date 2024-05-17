package org.example.TestRep.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Exam {
    @Id
    private String id;
    private String name;
    private LocalTime duration;
    // validate date
    private List<LocalDate> dates= new ArrayList<>();
    private List<Student> students= new ArrayList<>();
    private Center center;



}
