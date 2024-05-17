package org.example.TestRep.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@Document

public class Student {

    @Id
    private String id;
    private String name;
    private Integer grade;

}