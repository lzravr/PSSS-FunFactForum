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
<title>Admin Panel</title>
</head>
<body>
<form action="AdminServlet" method="get" id="searchBar">
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
					<li class="nav-item"><a class="nav-link" href="ProfileServlet?uid=${user.id }"  > <span
							data-feather="user"></span> <c:out value="${user.name }"></c:out>
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
						class="d-flex align-items-center text-muted" href="#" data-toggle="modal" data-target="#exampleModalCenter"> <span
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

			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"> 
			<div class="row">
			<div class="col-md-6">
		      <div class="my-3 p-3 bg-white rounded box-shadow">
		        <h6 class="border-bottom border-gray pb-2 mb-0">Users</h6>
		        
		        <c:forEach items="${users }" var="f">
			        <div class="media text-muted pt-3">
			          <img src='assets/img/${f.picture }' alt="${f.picture }" class="mr-2 rounded" style="height: 48px; width:48px;">
			          <div class="media-body pb-4 mb-0 small lh-125 border-bottom border-gray">
			            <div class="d-flex justify-content-between align-items-center w-100">
			              <strong class="text-gray-dark"><a href="ProfileServlet?uid=${f.id }">${f.name }</a></strong>
			             
			              	<form action="AdminServlet" method="post">
			              		<input type="text" name="action" value="delete" hidden>
			              		<input type="text" name="id" value="${f.id }" hidden>
			              		<input type="text" name="object" value="user" hidden>
			             		<button class="btn btn-danger btn-sm" type="submit" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample">
								    	<span data-feather="trash-2"></span>
								 </button>
			              	 </form>   
			              	              
			            </div>
			            <span class="d-block">@${f.username }</span>
			          </div>
			        </div>
			      
		        </c:forEach>
		        
		      </div>
		      </div>
		      
		      
		      
		      <div class="col-md-6">
		      <div class="my-3 p-3 bg-white rounded box-shadow">
		        <h6 class="border-bottom border-gray pb-2 mb-0">Categories</h6>
		        
		        <c:forEach items="${categories }" var="f">
			        <div class="media text-muted pt-3">
			          <img src='assets/img/${f.name }.jpg' alt="${f.name }.jpg" class="mr-2 rounded" style="height: 48px; width:48px;">
			          <div class="media-body pb-4 mb-0 small lh-125 border-bottom border-gray">
			            <div class="d-flex justify-content-between align-items-center w-100">
			              <strong class="text-gray-dark">${f.name }</strong>
			             
			              <form action="AdminServlet" method="post">
			              		
			              		<input type="text" name="action" value="delete" hidden>
			              		<input type="text" name="id" value="${f.id }" hidden>
			              		<input type="text" name="object" value="category" hidden>
			             		<button class="btn btn-danger btn-sm" type="submit" role="button">
								    	<span data-feather="trash-2"></span>
								 </button>
			              </form> 	
			              	              
			            </div>
			          </div>
			        </div>
			      
		        </c:forEach>
		        
		      </div>
		      </div>
	
			</div>

			</main>
		</div>
		
		<!-- Modal -->
		<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <form action="AdminServlet" method="post" enctype="multipart/form-data" id="addCatForm">
			      <div class="modal-body">
			          <div class="form-group">
			            <label for="recipient-name" class="col-form-label">Category Name:</label>
			            <input type="text" name="catName" class="form-control" id="recipient-name">
			          </div>
			          <div class="form-group">
			            <label for="message-text" class="col-form-label">Description:</label>
			            <textarea class="form-control" name="desc" id="message-text"></textarea>
			          </div>
				    <input type="file" name="file" />
				    			    
			        
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			        <a href="javascript:{}" onclick="document.getElementById('addCatForm').submit(); return false;">Save</a>
			      </div>
		      </form>
		    </div>
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