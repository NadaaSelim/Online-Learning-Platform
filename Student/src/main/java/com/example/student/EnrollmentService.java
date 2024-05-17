package com.example.student;

import com.example.student.model.Course;
import com.example.student.model.UiCourse;
import com.example.student.model.UiEnrollment;
import com.netflix.discovery.EurekaClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EnrollmentService {

    private final EurekaClient eurekaClient;


    @LoadBalanced // Enables client-side load balancing
    private final RestTemplate restTemplate;

    public EnrollmentService(EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
    }

    public List<UiEnrollment> getAllEnrollments(String studentId){
        // Get the service instance information from Eureka
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();
        // Use RestTemplate with the resolved service URL
        List<Course> courses = restTemplate.exchange(courseServiceUrl + "/api/courses/enrollments?studentid="+studentId,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Course>>() {}).getBody();

        if(courses != null){
            List<UiEnrollment> uiEnrollments = courses.stream().map(course -> {
                UiEnrollment uiEnrollment = new UiEnrollment();
                uiEnrollment.setName(course.getName());
                uiEnrollment.setInstructor(course.getInstructor().getInstructorName());
                uiEnrollment.setCategory(course.getCategory());
                uiEnrollment.setStatus(course.getStudents().stream().filter(student -> student.getId().equals(studentId)).findFirst().get().getStatus());
                return uiEnrollment;
            }).toList();
            return uiEnrollments;
        }
        return null;



    }
}
