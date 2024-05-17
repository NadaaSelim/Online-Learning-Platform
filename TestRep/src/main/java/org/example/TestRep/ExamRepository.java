package org.example.TestRep;

import org.example.TestRep.model.Center;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends MongoRepository<Center, String> {

}
