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
<link href="assets/css/profile.css" rel="stylesheet">
<title> <c:out value="${user.name }"></c:out> </title>
</head>
<body>
	<form action="PeopleServlet" method="get" id="searchBar">
	<nav
		class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
		<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="LoginServlet">Rocket Forum</a>
		<input class="form-control form-control-dark w-100" type="text" placeholder="Search" name="search" autocomplete="off"
			value="" aria-label="Search">
	<ul class="navbar-nav px-3">
<%-- 		<li class="nav-item text-nowrap"><a class="nav-link" href="#"></a> <c:out value="${user.name }"></c:out> </li> --%>
		<li class="nav-item text-nowrap"><a class="nav-link" href="LogoutServlet" >Sign out</a></li>
	</ul>
	</nav>
</form>

	<div class="container-fluid">
		<div class="row">
			<nav class="col-md-2 d-none d-md-block bg-light sidebar">
			<div class="sidebar-sticky">
				<ul class="nav flex-column">
					<li class="nav-item"><a class="nav-link active" href="LoginServlet">
							<span data-feather="home"></span> Dashboard <span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="ProfileServlet?uid=${sessionUser.id }"> <span
							data-feather="user"></span> <c:out value="${sessionUser.name }"></c:out>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="PeopleServlet"> <span
							data-feather="users"></span> Following
					</a></li>
					<li class="nav-item"><a class="nav-link" href="MessageServlet"> <span
							data-feather="mail"></span> Messages 
							<c:if test="${unread > 0 }"><span class="badge badge-danger">${unread }</span></c:if>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="ChatServlet"> <span
							data-feather="message-circle"></span> Chat
					</a></li>
					<li class="nav-item"><a class="nav-link" href="help.jsp"> <span
							data-feather="help-circle"></span> Help
					</a></li>
					<li class="nav-item"><a class="nav-link" href="LuckyServlet"> <span
							data-feather="fast-forward"></span> Feeling Lucky
					</a></li>
				</ul>

				<h6
					class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
					<span>Categories</span> <a
						class="d-flex align-items-center text-muted" href="#"> <span
						data-feather="plus-circle"></span>
					</a>
				</h6>
				<ul class="nav flex-column mb-2">
						<c:forEach items="${categories}" var="cat">
							<li class="nav-item"><a class="nav-link" href='CategoryServlet?cid=<c:out value="${cat.id }"></c:out>'> <span
								data-feather="hash"></span> <c:out value="${cat }"></c:out>
							</a></li>
						</c:forEach>
				</ul>
			</div>
			</nav>

			<main role="main" class="col-md-8 ml-sm-auto col-lg-10 px-6"> 
				<div class="container">
					<div class="row pt-5">					
				        <div class="col-md-6">
				          <div class="card flex-md-row mb-4 box-shadow h-md-250">
				            <div class="card-body d-flex flex-column align-items-start">
				              <strong class="d-inline-block mb-2 text-primary">ID: ${user.id }</strong>
				              <h3 class="mb-0">
				                <a class="text-dark" href="#">${user.name }</a>
				              </h3>
				              <div class="mb-1 text-muted">@${user.username }</div>
				              <p class="card-text mb-auto"><span data-feather="mail"> </span> ${user.email }</p>
				              
				              <c:if test="${user.id == sessionUser.id }">
							     <p>
								  <a class="btn btn-primary btn-sm" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
								    Edit
								  </a>					  
								 </p>
							  </c:if>
							  
							  <c:if test="${user.id != sessionUser.id }">
							     <p>
							     <div class="row">
							     <form action="PeopleServlet" method="post" style="padding-left: 10px; padding-right:5px; color:white">
							     
							     	 <input type="text" name="me" value="${sessionUser.id }" hidden>
							     	 <input type="text" name="who" value="${user.id }" hidden>
							     	 <input type="text" name="where" value="profile" hidden>
							     	 
							     	 <c:if test="${isFollowing == false }">
							     	 	 <input type="text" name="action" value="follow" hidden>
									 	 <button class="btn btn-primary btn-sm" type="submit" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample">
									    	Follow
									  	</button>
								  	 </c:if>
								  	 <c:if test="${isFollowing == true }">
								  	 	 <input type="text" name="action" value="unfollow" hidden>
									 	 <button class="btn btn-outline-primary btn-sm" type="submit" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample">
									    	Unfollow
									  	</button>
								  	 </c:if>
								  </form>
								 
								  <a class="btn btn-dark" data-toggle="collapse" href="#collapseMessage" role="button" aria-expanded="false" aria-controls="collapseExample">
								    <span data-feather="mail"></span>
								  </a>
								  
								  	
								 </div>			  
								 </p>
							  </c:if>
				              
				            </div>
				            <img class="card-img-right flex-auto d-none d-lg-block" src="assets/img/${user.picture }" alt="assets/img/${user.picture }/ne radi" style="width: 250px; height: 250px;" data-holder-rendered="true">
				          </div>
				        </div>
				        
				        <c:if test="${user.id == sessionUser.id }">
					        <form action="PhotoServlet" method="post" enctype="multipart/form-data">
					        	<small>Image must be .jpg</small> <br>
							    <input type="file" name="file" />
							    <input type="submit" />
							</form>
						</c:if>
				        
				     </div>
				     
<!-- 				     PORUKE -->
				     <div class="collapse" id="collapseMessage">
					  <div class="col-md-6 card card-body">
					  <form action="MessageServlet" method="post">
					    		<input type="text" name="me" value="${sessionUser.id }" hidden>
					     	    <input type="text" name="who" value="${user.id }" hidden>
					     	    <input type="text" name="where" value="profile" hidden>
					     	    <input type="text" name="action" value="send" hidden>
					    		<div class="input-group">
					    	
						    		<textarea class="form-control" name="message" rows="4" aria-label="With textarea" placeholder="Write your message..."></textarea>
							  		<div class="input-group-append">
							    		<button class="btn btn-dark" type="submit">Send</button>
							  		</div>
						 	
								</div>
						</form>
					  </div>
					</div>
				     
<!-- 					EDIT -->
					<div class="collapse" id="collapseExample" style="padding-bottom:20px">
					  <div class="card card-body">
					    <form action="ProfileServlet" method="post" class="needs-validation" novalidate>
					    	<input name="id" value="${sessionUser.id }" hidden>
							
							<div class="mb-3">
								<label for="name">Name <span class="text-muted"></span></label>
								<input type="text" class="form-control" id="name" name="name"
									placeholder="" value="${sessionUser.name }" required>
								<div class="invalid-feedback">Your name is required.</div>
							</div>
		
							<div class="mb-3">
								<label for="username">Username</label>
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text">@</span>
									</div>
									<input type="text" class="form-control" id="username"
										name="username" value="${sessionUser.username }" required>
									<div class="invalid-feedback" style="width: 100%;">Your
										username is required.</div>
								</div>
							</div>
		
							<div class="mb-3">
								<label for="email">Email <span class="text-muted"></span></label>
								<input type="email" class="form-control" id="email" name="email"
									value="${sessionUser.email }" required>
								<div class="invalid-feedback">Please enter a valid email
									address</div>
							</div>
		
							<div class="mb-3">
								<label for="password">Password <span class="text-muted"></span></label>
								<input type="password" class="form-control" id="password"
									name="password" placeholder=""  required>
								<div class="invalid-feedback">Please enter a valid password</div>
							</div>
		
							<div class="mb-3">
								<label for="repeatPassword">Repeat Password <span
									class="text-muted"></span></label> <input type="password"
									class="form-control" id="repeatPassword" name="repeatPassword"
									placeholder="" required>
								<div class="invalid-feedback">Please enter a valid password</div>
							</div>
		
							<hr class="mb-4">
		
							<button class="btn btn-primary btn-lg btn-block col-md-2" type="submit">Save</button>
							
						</form>
					  </div>
					</div>
			      </div>
			</main>
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

	<script>
		// Example starter JavaScript for disabling form submissions if there are invalid fields
						(
								function() {
									'use strict';

									window
											.addEventListener(
													'load',
													function() {
														// Fetch all the forms we want to apply custom Bootstrap validation styles to
														var forms = document
																.getElementsByClassName('needs-validation');

														// Loop over them and prevent submission
														var validation = Array.prototype.filter
																.call(
																		forms,
																		function(
																				form) {
																			form
																					.addEventListener(
																							'submit',
																							function(
																									event) {
																								if (form
																										.checkValidity() === false) {
																									event
																											.preventDefault();
																									event
																											.stopPropagation();
																								}
																								form.classList
																										.add('was-validated');
																							},
																							false);
																		});
													}, false);
								})();
	</script>
	
</body>
</html>