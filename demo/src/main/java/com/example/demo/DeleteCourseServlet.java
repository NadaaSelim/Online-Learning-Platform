package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet(name = "deleteCourse", value = "/deleteCourse/*")
@Stateless
public class DeleteCourseServlet extends HttpServlet {

    @EJB
    DELRequester requester;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // /{id}
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing course ID");
            return;
        }

        String id = pathInfo.substring(1); // Extract the ID from the path
        System.out.println("Deleting course with ID: " + id);

        requester.delete("http://localhost:8081/api/courses/delete/" + id);
        // Redirect back to the courses page after deletion
        resp.sendRedirect(req.getContextPath() + "/courses");
    }
    public void init(){
        requester=new DELRequester();
    }
}
