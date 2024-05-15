package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.ejb.Stateless;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Stateless
@WebServlet(name = "courses", value = "/courses")
public class CourseMang extends HttpServlet {

    public void init() {

    }

    public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {

        List<Course> courses = getCourses("http://localhost:8081/api/courses");
        if(courses == null){
            response.getWriter().println("<html><body>No courses found</body></html>");
            return;

        }
        req.setAttribute("courses", courses);
        //response.setContentType("text/html");
        //response.getWriter().println("<html><body>"+students.get(0).getName()+"</body></html>");
        req.getRequestDispatcher("/courses.jsp").forward(req, response);

    }
    public List<Course> getCourses(String courseService) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(courseService))
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();

                return mapper.readValue(httpResponse.body(), new TypeReference<List<Course>>() {});
            } else {
                System.out.println("Error: " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void destroy() {
    }
}