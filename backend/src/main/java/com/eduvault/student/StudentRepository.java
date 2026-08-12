package com.eduvault.student;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<StudentDocument, String> {
    
    Optional<StudentDocument> findByUserId(String userId);
}
