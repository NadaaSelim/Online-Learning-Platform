package com.example.student;

import com.example.student.model.Course;
import com.netflix.discovery.EurekaClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // Use RestTemplate for easier integration

import java.util.List;


@Service
public class CourseService {


    private final EurekaClient eurekaClient;

    @LoadBalanced // Enables client-side load balancing
    private final RestTemplate restTemplate;

    public CourseService(EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.eurekaClient = eurekaClient;
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
}
