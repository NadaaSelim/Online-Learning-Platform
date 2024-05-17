package com.example.demo;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@WebServlet(name = "deleteInstructor", value = "/deleteInstructor/*")
public class DeleteInstructorServlet {
    @EJB
    DELRequester requester;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo(); // /{id}
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing instructor ID");
            return;
        }

        String id = pathInfo.substring(1);
        requester.delete("http://localhost:8080/api/instructors/delete/"+id);
        resp.sendRedirect(req.getContextPath() + "/users");
    }

    public void init(){
        requester=new DELRequester();
    }
}
