package com.example.CourseManagement;


import com.example.CourseManagement.model.Enrollment;
import com.example.CourseManagement.model.Status;
import com.example.CourseManagement.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;


@Component
public class KafkaConsumerForEnrollments {

    private final CourseRepository courseRepository;

    public KafkaConsumerForEnrollments(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @KafkaListener(topics = "enroll-topic", groupId = "1")
    public void enroll(String message) throws JsonProcessingException {
        // Create ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        // Convert JSON string to a Map (flexible approach)
        Enrollment enrollment = mapper.readValue(message, Enrollment.class);

        // Access attributes using key
        String courseId = enrollment.courseid;
        String studentId = enrollment.studentid;

        System.out.println("Course ID: " + courseId);
        System.out.println("Student ID: " + studentId);

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        int EnrolledNums = courseRepository.countStudentsByIdAndStudentsStatus(courseId, Status.ACCEPTED);
        if (courseRepository.existsByIdAndStudentsId(courseId, studentId))
            System.out.println("You have already requested for enrollment");
        if (EnrolledNums >= course.getCapacity()) {
            System.out.println("Course is already full");
        }
        else {
            Student student = new Student();
            student.setId(studentId);
            course.getStudents().add(student);
            courseRepository.save(course);
            System.out.println("Student added to list successfully");

        }
    }

    @KafkaListener(topics = "drop-topic", groupId = "2")
    public void drop(String message) throws JsonProcessingException {
        // Create ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        // Convert JSON string to a Map (flexible approach)
        Enrollment enrollment = mapper.readValue(message, Enrollment.class);

        // Access attributes using key
        String courseId = enrollment.courseid;
        String studentId = enrollment.studentid;

        System.out.println("Course ID: " + courseId);
        System.out.println("Student ID: " + studentId);

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        if (!courseRepository.existsByIdAndStudentsIdAndStudentsStatus(courseId, studentId, Status.ACCEPTED)) {
            System.out.println("You are not registered in course");
        }
        List<Student> students = course.getStudents();
        Optional<Student> studentOptional = students.stream().filter(student -> student.getId().equals(studentId)).findAny();
        Student student = studentOptional.orElseThrow(() -> new RuntimeException("Student not found"));
        students.remove(student);

        courseRepository.save(course);
        System.out.println("Enrollment has been cancelled");

    }


}
