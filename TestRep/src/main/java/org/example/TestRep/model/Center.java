package org.example.TestRep.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Center {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String address;
    private String location;
    private String bio;


}
