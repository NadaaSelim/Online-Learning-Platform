package com.example.demo;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebServlet(name = "deleteStudent", value = "/deleteStudent/*")
public class DeleteStudentServlet extends HttpServlet{
    public void init(){}
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // /{id}
        resp.getWriter().println("<html><body>deleted</body></html>");
        //return;
       if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing instructor ID");
            return;
        }

      String id = pathInfo.substring(1); // Extract the ID from the path
        System.out.println("Deleting student with ID: " + id);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/students/delete/"+id))
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
    public void destroy(){}
}
