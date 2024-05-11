package com.example.CourseManagement;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    public List<Course> findCoursesByNameContainingIgnoreCase(String name);
    @Aggregation(pipeline = {
            "{ $unwind: '$reviewList' }",
            "{ $group: { _id: '$_id', averageRating: { $avg: '$reviewList.rating' } } }",
            "{ $sort: { averageRating: -1 } }"
    })
    List<Course> findAllByOrderByAverageRatingDesc();
    List<Course> findByCategory(Category name);
}
