package com.example.CourseManagement;

import com.example.CourseManagement.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;


@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

//    List<Course> findAllByOrderByAverageRatingDesc();
    List<Course> findCoursesByNameContainingIgnoreCase(String name);
//    @Aggregation(pipeline = {
//            "{ $match: { published: true } }",
//            "{ $lookup: { from: 'students', localField: 'students.id', foreignField: '_id', as: 'studentReviews' } }",
//            "{ $unwind: '$studentReviews' }",
//            "{ $group: { _id: '$ _id', avgRating: { $avg: '$studentReviews.review.rating' } } }",
//            "{ $lookup: { from: 'courses', localField: '_id', foreignField: '_id', as: 'courseDetails' } }",
//            "{ $unwind: '$courseDetails' }",
//            "{ $project: { courseDetails: { $exclude: 'id' }, averageRating: 1 } }"
//    })
//    public List<Course> findByPublishedOrderByAverageRatingDesc() ;
    List<Course> findByCategory(Category name);
    List<Course> findByStudents_IdAndStudents_Status(String studentId, Status status);

    boolean existsByIdAndStudentsId(String courseId, String studentId);
    int countStudentsByIdAndStudentsStatus(String courseId, Status status);
    boolean existsByIdAndStudentsIdAndStudentsStatus(String courseId, String studentId,Status status);


    List<Course> findByPublished(boolean b);

    List<Course> findByStudents_Id(String studentid);

    //boolean existsByIdAndInstructor_Id(String courseId,String instructorId)
    //void removeByIdAndStudentsId(String courseId,String studentId);
}
