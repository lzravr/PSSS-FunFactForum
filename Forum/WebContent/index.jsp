<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" href="assets/img/fav.gif" type="image/x-icon">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<link href="assets/css/signin.css" rel="stylesheet">
<title>Rocket Forum</title>
</head>
<body>
	 <form class="form-signin" action="LoginServlet" method="post">
      <div class="text-center mb-4">
        <img class="mb-4" src="assets/img/fav.gif" alt="" width="144" height="144">
        <h1 class="h3 mb-3 font-weight-normal">Rocket Forum</h1>
      </div>

      <div class="form-label-group">
        <input type="username" name="inputUsername" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
        <label for="inputUsername">Username</label>
      </div>

      <div class="form-label-group">
        <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Password" required>
        <label for="inputPassword">Password</label>
      </div>
 	  <p style="color:red; width: 100%; padding: 0px; font-size:11pt;"><c:out value="${requestScope.error }"></c:out></p>

<!--       <div class="checkbox mb-3"> -->
<!--         <label> -->
<!--           <input type="checkbox" value="remember-me"> Remember me -->
<!--         </label> -->
<!--       </div> -->
      <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      <p>Don't have an account? <a href="register.jsp">Register</a></p>
      <p class="mt-5 text-muted text-center">&copy; PSSS 2018. Lazar Avramovic</p>
      <p class="mb-3 text-muted text-center"><a href="help.jsp">Help</a></p>
    </form>
	
	

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>
</html>