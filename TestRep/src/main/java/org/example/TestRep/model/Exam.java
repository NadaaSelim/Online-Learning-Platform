package org.example.TestRep.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.Duration;
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
    private int duration;

    @NotNull
    private List<LocalDate> dates= new ArrayList<>();
    private List<Student> students= new ArrayList<>();
    private String cid;



}
