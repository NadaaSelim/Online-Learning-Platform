package com.example.instructor;

import com.example.instructor.models.Course;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
public class CourseService {


    private final EurekaClient eurekaClient;


    @LoadBalanced // Enables client-side load balancing
    private final RestTemplate restTemplate;




    public CourseService(EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;

    }

    @CircuitBreaker(name="ViewCourses", fallbackMethod = "fallBackCourses")
    public List<Course> getAllCourses() {
        try {
        // Get the service instance information from Eureka
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();

        // Use RestTemplate with the resolved service URL
            String jsonResponse =   restTemplate.getForObject(courseServiceUrl + "/api/courses", String.class);
            ObjectMapper objectMapper = new ObjectMapper();
        List<Course> courses = null;
        courses = objectMapper.readValue(jsonResponse, new TypeReference<List<Course>>() {});
            return  courses;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Service failed!", e);
        }

                //restTemplate.exchange(courseServiceUrl + "/api/courses",
                  //      HttpMethod.GET, null,
                    //    new ParameterizedTypeReference<List<Course>>() {}).getBody();

    }
    public ResponseEntity<Object>fallBackCourses(Throwable exception){
        //List<Course> fallbackProducts = Collections.singletonList(new Course("0"," Service is down try later "));
        return new ResponseEntity<>(" Service is down please try later ", HttpStatus.SERVICE_UNAVAILABLE);
        //return new ResponseEntity<String>("Course service is down", HttpStatus.OK);

    }


    public Course addCourse(Course course){

        // Get the service instance information from Eureka
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();

        // Use RestTemplate with the resolved service URL
        HttpEntity<Course> request = new HttpEntity<>(course);
        return  restTemplate
                .postForObject(courseServiceUrl + "/api/courses/add",  request, Course.class);

    }

    public List<Course> findCourseByName(String name){
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();
        // Use RestTemplate with the resolved service URL
        return restTemplate.exchange(courseServiceUrl + "/api/courses/name?name="+name,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Course>>() {}).getBody();
    }

    public List<Course> findCategory(String category){
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();
        return restTemplate.exchange(courseServiceUrl + "/api/courses/category?category="+category,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Course>>() {}).getBody();

    }

    public List<Course> getCoursesByRating(){
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();
        return restTemplate.exchange(courseServiceUrl + "/api/courses/sorted",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Course>>() {}).getBody();

    }


    public String accept(String courseid, String studentid, String instrId) {
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();
        return restTemplate.exchange(courseServiceUrl+"/api/courses/accept/"+courseid+"/"+studentid+"/"+instrId+"/",HttpMethod.PUT,null,String.class).getBody();


    }

    public String reject(String courseid, String studentid, String instrId) {
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();
        return restTemplate.exchange(courseServiceUrl+"/api/courses/reject/"+courseid+"/"+studentid+"/"+instrId+"/",HttpMethod.PUT,null,String.class).getBody();


    }
}
