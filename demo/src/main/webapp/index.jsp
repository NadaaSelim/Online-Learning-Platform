<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>
<body>
<h1 class="text-center p-5"><%= "Login" %>
</h1>
<br/>
<div class=" justify-content-sm-center mx-auto  p-5">
    <form id="loginForm" method="post">

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password"  placeholder="Password">
        </div>
        <div class="d-flex mt-4"><button type="submit" class="btn btn-secondary">Login</button></div>

    </form>
    <p id="error"></p>
</div>
<script>
    document.getElementById("loginForm").addEventListener("submit", function(event){
        event.preventDefault();
      if(document.getElementById("password").value === "1234")
          window.location.href = "home";
      else
          document.getElementById("error").innerText = "Wrong Password!";
    })
</script>
</body>
</html>