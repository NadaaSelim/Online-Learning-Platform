package com.example.student;

import com.example.student.model.Course;
import com.example.student.model.Enrollment;
import com.example.student.model.Review;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // Use RestTemplate for easier integration

import java.util.List;


@Service
public class CourseService {


    private final EurekaClient eurekaClient;

    private final KafkaTemplate<String, String> kafkaTemplate;

    

    @LoadBalanced // Enables client-side load balancing
    private final RestTemplate restTemplate;

    public CourseService(EurekaClient eurekaClient, KafkaTemplate<String, String> kafkaTemplate, RestTemplate restTemplate) {
        this.eurekaClient = eurekaClient;
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
    }

    public List<Course> getAllCourses() {
        // Get the service instance information from Eureka
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();

        // Use RestTemplate with the resolved service URL
        return restTemplate.exchange(courseServiceUrl + "/api/courses",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Course>>() {}).getBody();
    }

    public void enrollCourse(Enrollment enrollment) throws JsonProcessingException {
        String data = new ObjectMapper().writeValueAsString(enrollment);
        kafkaTemplate.send("enroll-topic", data);
    }

    public void dropCourse(Enrollment enrollment) throws JsonProcessingException {
        String data = new ObjectMapper().writeValueAsString(enrollment);
        kafkaTemplate.send("drop-topic", data);
    }


    public String reviewCourse( String courseid, String studentid, Review review) {

        // Get the service instance information from Eureka
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();
        String url = courseServiceUrl + "/api/courses/review/"+courseid+"/"+studentid;

        HttpEntity<Review> request = new HttpEntity<>(review);
        return restTemplate.postForObject(url, request, String.class);



    }
}
