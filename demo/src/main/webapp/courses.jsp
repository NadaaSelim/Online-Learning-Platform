<%@ page import="com.example.demo.Course" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Management</title>
</head>
<body>
<h1>Course Management</h1>
<form action="${pageContext.request.contextPath}/editCourse" method="post">
    <table >
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
            List<Course> courses = (List<Course>) request.getAttribute("courses");
            if (courses != null) {
                for (Course course : courses) {
        %>
        <tr>
            <td>_<%= course.getId() %>></td>
            <td><input type="text" name="name_<%= course.getId() %>" value="<%= course.getName() %>"></td>
            <td><input type="number" name="duration_<%= course.getId() %>" value="<%= course.getDuration() %>"></td>
            <td><input type="number" name="capacity_<%= course.getId() %>" value="<%= course.getCapacity() %>"></td>
            <td><input type="text" name="category_<%= course.getId() %>" value="<%= course.getCategory() %>"></td>
            <td>
                <a href="<%= request.getContextPath() %>/deleteCourse/<%= course.getId() %>">REMOVE</a>
                <input type="submit" value="SUBMIT" formaction="<%= request.getContextPath() %>/editCourse/<%= course.getId() %>">
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</form>
</body>
</html>
