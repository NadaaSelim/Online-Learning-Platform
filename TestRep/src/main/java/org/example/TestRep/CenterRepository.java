package org.example.TestRep;

import org.example.TestRep.model.Center;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CenterRepository extends MongoRepository<Center, String> {
    Optional<Center> findCenterByEmail(String email);
}
