<%@ page import="com.example.demo.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.Student" %>
<%@ page import="com.example.demo.Course" %><%--
  Created by IntelliJ IDEA.
  User: menna
  Date: 5/15/2024
  Time: 12:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Get the courses list from request attribute
    List<Course> courses = (List<Course>) request.getAttribute("courses");
%>

<html>
<head>
    <title>Course Usage</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Duration</th>
        <th>Capacity</th>
        <th>Category</th>
        <th>Average Rating</th>
        <th>Reviews</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <%

        for (Course course : courses) {
    %>
    <tr>
        <td><%= course.getId() %></td>
        <td><%= course.getName() %></td>
        <td><%= course.getDuration() %></td>
        <td><%= course.getCapacity() %></td>
<%--&lt;%&ndash;        <td><%= course.getInstrucor().getNam %></td>&ndash;%&gt;--%>
        <td><%= course.getCategory() %></td>
        <td><%= course.getAverageRating() %></td>
        <td>
            <%-- Iterate over each review for the current course --%>
            <ul>
                <% for (Student student : course.getStudents()) { %>
                <% if (student.getReview() != null) { %>
                <li>
                    Rating: <%= student.getReview().getRating() %>,
                    Comment: <%= student.getReview().getComment() %>
                </li>
                <% } %>
                <% } %>
            </ul>
        </td>

    </tr>
    <%-- ReviewCourses.jsp --%>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
