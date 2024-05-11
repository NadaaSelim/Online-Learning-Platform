package com.example.authentication.instructor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends MongoRepository<Instructor, String>{

    Optional<Instructor> findInstructorByEmail(String email);
}
