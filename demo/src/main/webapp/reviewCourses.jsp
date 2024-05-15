<%@ page import="com.example.demo.Course" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: menna
  Date: 5/15/2024
  Time: 12:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Get the instructors list from request attribute
    List<Course> courses = (List<Course>) request.getAttribute("courses");
%>

<html>
<head>
    <title>Review Courses</title>
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
<%--//        <td><%= course.getInstructor().getName() %></td>--%>
        <td><%= course.getDuration() %></td>
        <td><%= course.getCapacity() %></td>
        <td><%= course.getCategory() %></td>
        <td><a href="<%=request.getContextPath() %>/publishCourse/<%=course.getId()%>">PUBLISH</a></td>
        <td><a href="<%=request.getContextPath() %>/deleteCourse/<%=course.getId()%>">REMOVE</a></td>
    </tr>reviewCourses.jsp
    <%

        }
    %>
    </tbody>
</table>
</body>
</html>
