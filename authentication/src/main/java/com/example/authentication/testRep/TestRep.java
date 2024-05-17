package com.example.authentication.testRep;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class TestRep {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Center center;


}
