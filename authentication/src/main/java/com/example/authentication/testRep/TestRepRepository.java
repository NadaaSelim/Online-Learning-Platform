package com.example.authentication.testRep;

import com.example.authentication.student.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepRepository extends MongoRepository<TestRep, String> {

    TestRep findTestRepById(String id);
    Optional<TestRep> findTestRepByByEmail(String email);
}
