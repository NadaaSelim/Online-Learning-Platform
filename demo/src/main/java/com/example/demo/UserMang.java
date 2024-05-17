package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.ejb.EJB;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "users", value = "/users")
public class UserMang extends HttpServlet {
    private String message;
    List<Instructor> instructors ;
    List<Student> students ;
    @EJB
    private IRequester requester;

    public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
      //  InitialContext context= null;
        //      context = new InitialContext();
//            System.out.println(context.getEnvironment());
//            IRequester requester1=(IRequester) context.lookup("requester");
            instructors = requester.fetchDataFromUrl("http://localhost:8080/api/instructors/all", Instructor.class);
            students = requester.fetchDataFromUrl("http://localhost:8080/api/students/all", Student.class);
            if(students == null && instructors==null){
                response.getWriter().println("<html><body>No instructors or students found</body></html>");
                return;
            }
            req.setAttribute("instructors", instructors);
            req.setAttribute("students", students);
            response.setContentType("text/html");
            //response.getWriter().println("<html><body>"+students.get(0).getName()+"</body></html>");
            req.getRequestDispatcher("/usersDetails.jsp").forward(req, response);


    }

    public void init() {
        message = "Hello World!";
        requester=new Requester();
    }
    public void destroy(){}
}

