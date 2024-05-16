package org.example.TestRep;

import org.example.TestRep.model.Center;
import org.example.TestRep.model.TestRep;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepRepository  extends MongoRepository<TestRep, String> {
}
