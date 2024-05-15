package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.ejb.Stateless;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Stateless
@WebServlet(name = "users", value = "/users")
public class UserMang extends HttpServlet {
    private String message;
//    @Inject
//    private Client client;

    public void init() {
        message = "Hello World!";
    }


    public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        List<Instructor> instructors = getInstructors("http://localhost:8080/api/instructors/all");
        List<Student> students = getStudents("http://localhost:8080/api/students/all");

        req.setAttribute("instructors", instructors);
        req.setAttribute("students", students);
        response.setContentType("text/html");
        //response.getWriter().println("<html><body>"+students.get(0).getName()+"</body></html>");
        req.getRequestDispatcher("/usersDetails.jsp").forward(req, response);
    }

    public List<Instructor> getInstructors(String authService) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(authService))
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();

                return mapper.readValue(httpResponse.body(), new TypeReference<List<Instructor>>() {});
            } else {
                System.out.println("Error: " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getStudents(String authService) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(authService))
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(httpResponse.body(), new TypeReference<List<Student>>() {});
            } else {
                System.out.println("Error: " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void destroy(){}
}

