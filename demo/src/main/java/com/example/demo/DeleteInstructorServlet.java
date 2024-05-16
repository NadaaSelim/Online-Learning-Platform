package com.example.demo;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.ejb.Stateless;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet(name = "deleteInstructor", value = "/deleteInstructor/*")
@Stateless
public class DeleteInstructorServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // /{id}
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing instructor ID");
            return;
        }

        String id = pathInfo.substring(1); // Extract the ID from the path
        // Here you would add code to delete the instructor with the given ID from your data source
        // For now, we will just print the ID to the console for demonstration
        System.out.println("Deleting instructor with ID: " + id);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/instructors/delete/"+id))
                .DELETE()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() != 200) {

                System.out.println("Error: " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        // Redirect back to the users page after deletion
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
