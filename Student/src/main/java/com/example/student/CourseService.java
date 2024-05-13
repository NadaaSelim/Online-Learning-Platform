package com.example.student;

import com.example.student.model.Course;
import com.example.student.model.Enrollment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
        String data = enrollment.toString();
        kafkaTemplate.send("enroll-topic", data);
    }
}
