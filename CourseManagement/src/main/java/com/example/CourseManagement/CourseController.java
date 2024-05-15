package com.example.CourseManagement;

import com.example.CourseManagement.model.Review;
import com.example.CourseManagement.model.Status;
import com.example.CourseManagement.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private final CourseRepository courserepo;
    @Autowired
    private CourseService courseService;

    public CourseController(CourseRepository courserepo) {
        this.courserepo = courserepo;
    }

    @GetMapping("")
    public List<Course> getCourses() {
        return courserepo.findByPublished(true);
    }

    @GetMapping("/unpub")
    public List<Course> getUnpubCourses(){
        return courserepo.findByPublished(false);
    }
    @PutMapping("/publish/{id}")
    public void publish(@PathVariable("id") String id){
        Optional<Course> courseOptional = courserepo.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.setPublished(true);
            courserepo.save(course);
            }
    }
    @PutMapping("/edit/{id}")
    public void edit(@PathVariable("id") String id,@RequestBody Course course){
        Optional<Course> existingCourse = courserepo.findById(id);
        if (existingCourse.isPresent()) {
            Course updatedCourse = existingCourse.get();
            updatedCourse.setName(course.getName());
            updatedCourse.setDuration(course.getDuration());
            updatedCourse.setCapacity(course.getCapacity());
            updatedCourse.setCategory(course.getCategory());

            courserepo.save(updatedCourse);
        }
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        courserepo.deleteById(id);
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


    @GetMapping("/all/{id}")
    public List<Course> getEnrolledCourses(@PathVariable("id") String id){
        return courserepo.findByStudents_IdAndStudents_Status(id,Status.ACCEPTED);
    }
    @GetMapping("/sorted")
    public List<Course> getSortedCourses(){
        return courseService.findAllByOrderByAverageRatingDesc();
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

    @PutMapping("/accept/{courseid}/{studentid}/{instrId}")
    public String acceptEnroll(@PathVariable("courseid") String courseid, @PathVariable("studentid") String studentid, @PathVariable("instrId") String instrId) {
        Course course = courserepo.findById(courseid).orElseThrow(() -> new RuntimeException("Course not found"));
        if (!course.getInstructor().getId().equals(instrId))
            return "You are not the instructor to accept enrollments";
        List<Student> students = course.getStudents();
        Student student = students.stream()
                .filter(stud -> stud.getId().equals(studentid))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Student not found"));
        if (student.getStatus().equals(Status.ACCEPTED))
            return "Student is already enrolled";
        if (student.getStatus().equals(Status.REJECTED))
            return "Student has already been rejected";

        student.setStatus(Status.ACCEPTED);
        courserepo.save(course);
        return "Student has been enrolled";
    }

    @PutMapping("/reject/{courseid}/{studentid}/{instrId}")
    public String rejectEnroll(@PathVariable("courseid") String courseid, @PathVariable("studentid") String studentid, @PathVariable("instrId") String instrId) {
        Course course = courserepo.findById(courseid).orElseThrow(() -> new RuntimeException("Course not found"));
        if (!course.getInstructor().getId().equals(instrId))
            return "You are not the instructor to reject enrollments";
        List<Student> students = course.getStudents();
        Student student = students.stream()
                .filter(stud -> stud.getId().equals(studentid))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Student not found"));
        if (student.getStatus().equals(Status.ACCEPTED))
            return "Student is already enrolled";
        if (student.getStatus().equals(Status.REJECTED))
            return "Student has already been rejected";

        student.setStatus(Status.REJECTED);
        courserepo.save(course);
        return "Student has been rejected";
    }


}

