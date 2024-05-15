package com.example.demo;

import java.io.*;
import java.util.List;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

@Stateless
@WebServlet(name = "home", value = "/home")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<a href=users>" + "View And Manage Users" + "</a><br>");
        out.println("<a href=review>" + "Review Unpublished Courses" + "</a><br>");
        out.println("<a href=courses>" + "View And Manage Courses" + "</a><br>");
//        out.println("<a href="UserMang">" + "View And Manage Users" + "</a>");
        out.println("<a href=coursesUsage>" + "Track Courses" + "</a><br>");

        out.println("</body></html>");
    }

    public void destroy() {
    }
}