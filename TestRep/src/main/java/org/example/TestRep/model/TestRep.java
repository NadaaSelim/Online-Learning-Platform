package org.example.TestRep.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
