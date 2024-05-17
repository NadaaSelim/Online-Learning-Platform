package org.example.TestRep.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Student {

    private String id;
    private String name;
    private Integer grade;

}