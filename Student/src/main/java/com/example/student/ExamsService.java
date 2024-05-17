package com.example.student;

import com.example.student.model.Review;
import com.example.student.model.Student;
import com.netflix.discovery.EurekaClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamsService {

    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;

    public ExamsService(EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
    }

    public List<Exam> getAllExams(String centerId){
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("testrep-service", false).getHomePageUrl();

        // Use RestTemplate with the resolved service URL
        List<Exam> exams =  restTemplate.exchange(courseServiceUrl + "/Center/exams",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Exam>>() {}).getBody();
        List<Exam> centerExams = new ArrayList<>();
        for (Exam exam : exams){
            if (exam.getCid().equals(centerId)){
                centerExams.add(exam);
            }
        }
        return centerExams;
    }

    public List<Center> getCenters(){
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("testrep-service", false).getHomePageUrl();

        // Use RestTemplate with the resolved service URL
        return restTemplate.exchange(courseServiceUrl + "/Center/all",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Center>>() {}).getBody();

    }

    public boolean register(String examId, Student student) {

        String courseServiceUrl = eurekaClient.getNextServerFromEureka("CourseManagement", false).getHomePageUrl();
        String url = courseServiceUrl + "/api/courses/review/"+examId;

        HttpEntity<Student> request = new HttpEntity<>(student);
        return restTemplate.postForObject(url, request, Boolean.class);
    }
}
