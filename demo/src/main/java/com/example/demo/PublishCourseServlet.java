package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.ejb.Stateless;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet(name = "publishCourse", value = "/publishCourse/*")
@Stateless
public class PublishCourseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // /{id}
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing course ID");
            return;
        }

        String id = pathInfo.substring(1); // Extract the ID from the path
        System.out.println("Publishing course with ID: " + id);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/courses/publish/" + id))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() != 200) {
                System.out.println("Error: " + httpResponse.statusCode());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to publish course");
                return;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while trying to publish the course");
            return;
        }

        // Redirect back to the courses page after publishing
        resp.sendRedirect(req.getContextPath() + "/courses");
    }
}
