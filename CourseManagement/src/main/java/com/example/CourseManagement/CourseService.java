package com.example.CourseManagement;

import com.example.CourseManagement.model.Student;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAllByOrderByAverageRatingDesc() {
        List<Course> courses = courseRepository.findByPublished(true);
        for (Course course : courses) {
            double totalRating = 0;
            int totalReviews = 0;
            for (Student student : course.getStudents()) {
                if (student.getReview() != null) {
                    totalRating += student.getReview().getRating();
                    totalReviews++;
                }
            }
            double averageRating = totalReviews > 0 ? totalRating / totalReviews : 0;
            course.setAverageRating(averageRating);
        }
        courses.sort(Comparator.comparingDouble(Course::getAverageRating).reversed());
        return courses;
    }
}
