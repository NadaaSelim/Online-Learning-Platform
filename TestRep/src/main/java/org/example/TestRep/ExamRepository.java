package org.example.TestRep;

import org.example.TestRep.model.Center;
import org.example.TestRep.model.Exam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends MongoRepository<Exam, String> {
    List<Exam> findByCid(String cid);

}
