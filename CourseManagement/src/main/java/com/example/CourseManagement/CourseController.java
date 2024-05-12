package com.example.CourseManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private final CourseRepository courserepo;

    public CourseController(CourseRepository courserepo) {
        this.courserepo = courserepo;
    }

    @GetMapping("")
    public List<Course> getCourses() {
        return courserepo.findAll();
    }
    @PostMapping("/add")
    public Course addCourse(@RequestBody Course course) {
        return courserepo.save(course);
    }

    @GetMapping("/id")      //id?=[id]
    public Optional<Course> getCourseById(@RequestParam("id") String id) {
        return courserepo.findById(id);
    }

    @GetMapping("/name")
    public List<Course> findCourseByName(@RequestParam("name") String name) {
        return courserepo.findCoursesByNameContainingIgnoreCase(name);
    }

    @GetMapping("/category")
    public List<Course> findCourseByCategory(@RequestParam("category") String category) {
        return courserepo.findByCategory(Category.valueOf(category));
    }

    @GetMapping("/all")
    public List<Course> getCoursesByRating() {
        return courserepo.findAllByOrderByAverageRatingDesc();
    }

    @PostMapping("/enroll/{courseid}/{studentid}")
    public String addStudentToCourse(@PathVariable("courseid") String courseid, @PathVariable("studentid") String studentid) {
        Course course = courserepo.findById(courseid).orElseThrow(() -> new RuntimeException("Course not found"));
        // int EnrolledNums = courserepo.countAcceptedStudentsInCourse(courseid);
        int EnrolledNums = courserepo.countStudentsByIdAndStudentsStatus(courseid, Status.ACCEPTED);
        if (courserepo.existsByIdAndStudentsId(courseid, studentid))
            return "You have already requested for enrollment";
        if (EnrolledNums >= course.getCapacity()) {
            return ("Course is already full");
        }
        Student student = new Student();
        student.setId(studentid);
        course.getStudents().add(student);
        courserepo.save(course);
        return "You have been added to the enrollment Waiting List.";
    }

    @DeleteMapping("/cancel/{courseid}/{studentid}")
    public String removeStudentToCourse(@PathVariable("courseid") String courseid, @PathVariable("studentid") String studentid) {
        Course course = courserepo.findById(courseid).orElseThrow(() -> new RuntimeException("Course not found"));
        if (!courserepo.existsByIdAndStudentsIdAndStudentsStatus(courseid, studentid, Status.ACCEPTED)) {
            return "You are not registered in course";
        }
        List<Student> students = course.getStudents();
        Optional<Student> studentOptional = students.stream().filter(student -> student.getId().equals(studentid)).findAny();
        Student student = studentOptional.orElseThrow(() -> new RuntimeException("Student not found"));
        students.remove(student);

        //students.removeIf(student -> student.getId().equals(studentid));

        courserepo.save(course);
        //if(removed)
        return "Enrollement has been cancelled";
        //else return "Could not unregister. Please Try Again.";

    }

    @PostMapping("/review/{courseid}/{studentid}")
    public String addReview(@PathVariable("courseid") String courseid, @PathVariable("studentid") String studentid, @RequestBody Review review) {
        Course course = courserepo.findById(courseid).orElseThrow(() -> new RuntimeException("Course not found"));
        if (!courserepo.existsByIdAndStudentsIdAndStudentsStatus(courseid, studentid, Status.ACCEPTED)) {
            return "You are not registered in course";
        }
        List<Student> students = course.getStudents();
        Student student = students.stream()
                .filter(stud -> stud.getId().equals(studentid))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setReview(review);
        courserepo.save(course);
        return review.toString();
    }


}