package com.example.student;

import com.netflix.discovery.EurekaClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExamsService {

    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;

    public ExamsService(EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
    }

    public String getAllExams(){
            return "All Exams";
    }

    public List<Center> getCenters(){
        String courseServiceUrl = eurekaClient.getNextServerFromEureka("testrep-service", false).getHomePageUrl();

        // Use RestTemplate with the resolved service URL
        return restTemplate.exchange(courseServiceUrl + "/Center/all",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Center>>() {}).getBody();

    }
}
