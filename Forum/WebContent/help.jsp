<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" href="assets/img/fav.gif" type="image/x-icon">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<link href="assets/css/dashboard.css" rel="stylesheet">
<title>Help</title>
</head>
<body>
	<nav
		class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
		<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="LoginServlet">Rocket Forum</a>
		
	<ul class="navbar-nav px-3">
<%-- 		<li class="nav-item text-nowrap"><a class="nav-link" href="#"></a> <c:out value="${user.name }"></c:out> </li> --%>
		
	</ul>
	</nav>
	
	<div class="container pt-5 mt-3">
      <div class="starter-template">
        <h1>This is a help page</h1>
        <p class="lead">How to use:</p>
        <ul class="list-group list-group-flush">
		  <li class="list-group-item">Register your account and login</li>
		  <li class="list-group-item">Change your profile picture under profile</li>
		  <li class="list-group-item">See your followers, who you follow and who you should follow</li>
		  <li class="list-group-item">User search bar to quickly find people</li>
		  <li class="list-group-item">Send personal messages to people and manage them under messages</li>
		  <li class="list-group-item">Chat with all online users</li>
		  <li class="list-group-item">Post and browse various fun facts about various topics</li>
		  <li class="list-group-item">Don't forget to rate them</li>
		</ul>
      </div>
    </div>

    
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>


	<!-- Icons -->
	<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
	<script>
		feather.replace()
	</script>
	

	
</body>
</html>