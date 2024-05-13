package com.example.instructor;

import com.example.instructor.models.Course;
import com.netflix.discovery.EurekaClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
public class CourseService {


    private final EurekaClient eurekaClient;

    @LoadBalanced // Enables client-side load balancing
    private final RestTemplate restTemplate;
    private  String courseServiceUrl ;

    public CourseService(EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
        this.courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();
    }

    public List<Course> getAllCourses() {
        // Get the service instance information from Eureka
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();

        // Use RestTemplate with the resolved service URL
        return restTemplate.exchange(courseServiceUrl + "/api/courses",
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Course>>() {}).getBody();
    }

    public Course addCourse(Course course){

        // Get the service instance information from Eureka
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();

        // Use RestTemplate with the resolved service URL
        HttpEntity<Course> request = new HttpEntity<>(course);
        return  restTemplate
                .exchange(courseServiceUrl + "/api/courses/add", HttpMethod.POST, request, new ParameterizedTypeReference<Course>() {}).getBody();

    }

    public List<Course> findCourseByName(String name){

        // Use RestTemplate with the resolved service URL
        return restTemplate.exchange(courseServiceUrl + "/api/courses/name?name="+name,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Course>>() {}).getBody();
    }

    public List<Course> findCategory(String category){
        return restTemplate.exchange(courseServiceUrl + "/api/courses/category?category="+category,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Course>>() {}).getBody();

    }

    public List<Course> getCoursesByRating(){
        return restTemplate.exchange(courseServiceUrl + "/api/courses/all",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Course>>() {}).getBody();

    }


}
