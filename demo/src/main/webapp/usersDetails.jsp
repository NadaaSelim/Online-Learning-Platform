<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.demo.Instructor" %>
<%@ page import="com.example.demo.Student" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Users</title>
</head>
<%
    // Get the instructors list from request attribute
    List<Instructor> instructors = (List<Instructor>) request.getAttribute("instructors");
    List<Student> students = (List<Student>) request.getAttribute("students");
%>
<body>
<h1>Instructors</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Affiliation</th>
        <th>Years of Experience</th>
        <th>Bio</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
        <%
                // Iterate over the instructors list
                for (Instructor instructor : instructors) {
            %>
    <tr>
        <td><%= instructor.getId() %></td>
        <td><%= instructor.getName() %></td>
        <td><%= instructor.getEmail() %></td>
        <td><%= instructor.getPassword() %></td>
        <td><%= instructor.getAffiliation() %></td>
        <td><%= instructor.getYearsOfExperience() %></td>
        <td><%= instructor.getBio() %></td>
        <td><a href=<%=request.getContextPath() %>"/deleteInstructor/<%= instructor.getId() %>">DELETE</a></td>
    </tr>
<% } %>
                </tbody>
</table>

<h1>Students</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Affiliation</th>
        <th>Bio</th>
        <th>Action</th>

    </tr>
    </thead>
    <tbody>
        <%
                // Iterate over the students list
                for (Student student : students) {
            %>
    <tr>
        <td><%= student.getId() %></td>
        <td><%= student.getName() %></td>
        <td><%= student.getEmail() %></td>
        <td><%= student.getPassword() %></td>
        <td><%= student.getAffiliation() %></td>
        <td><%= student.getBio() %></td>
        <td><a href="<%= request.getContextPath() %>/deleteStudent/<%= student.getId() %>">DELETE</a></td>
    </tr>
<% } %>
    </tbody>
</table>
</body>
</html>
