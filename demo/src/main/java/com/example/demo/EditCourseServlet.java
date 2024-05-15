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

@WebServlet(name = "editCourse", value = "/editCourse/*")
@Stateless
public class EditCourseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // /{id}
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing course ID");
            return;
        }

        String id = pathInfo.substring(1); // Extract the ID from the path
        String name = req.getParameter("name_" + id);
        String durationStr = req.getParameter("duration_" + id);
        String capacityStr = req.getParameter("capacity_" + id);
        String category = req.getParameter("category_" + id);

        // Validate and convert the input values
        int duration = Integer.parseInt(durationStr);
        int capacity = Integer.parseInt(capacityStr);

        // Construct the JSON payload
        String jsonPayload = String.format(
                "{\"name\":\"%s\",\"duration\":%d,\"capacity\":%d,\"category\":\"%s\"}",
                 name, duration, capacity, category
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/courses/edit/" + id))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() != 200) {
                System.out.println("Error: " + httpResponse.statusCode());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update course");
                return;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while trying to update the course");
            return;
        }

        // Redirect back to the courses page after updating
        resp.sendRedirect(req.getContextPath() + "/courses");
    }
}
