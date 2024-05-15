package com.example.CourseManagement;

import com.example.CourseManagement.model.Status;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findCoursesByNameContainingIgnoreCase(String name);
    @Aggregation(pipeline = {
            "{ $unwind: '$reviewList' }",
            "{ $group: { _id: '$_id', averageRating: { $avg: '$reviewList.rating' } } }",
            "{ $sort: { averageRating: -1 } }"
    })
    List<Course> findAllByOrderByAverageRatingDesc();
    List<Course> findByCategory(Category name);
    List<Course> findByStudents_IdAndStudents_Status(String studentId, Status status);
    List<Course> findCoursesByPublishedTrue();
    boolean existsByIdAndStudentsId(String courseId, String studentId);
    //Student findByIdAndStudentsId(String courseId, String studentId);
    int countStudentsByIdAndStudentsStatus(String courseId, Status status);
    boolean existsByIdAndStudentsIdAndStudentsStatus(String courseId, String studentId,Status status);

    List<Course> findCoursesByPublishedFalse();

    List<Course> findByPublishedFalse();

    List<Course> findByPublishedTrue();

    List<Course> findByPublished(boolean b);
    //boolean existsByIdAndInstructor_Id(String courseId,String instructorId)
    //void removeByIdAndStudentsId(String courseId,String studentId);
}
