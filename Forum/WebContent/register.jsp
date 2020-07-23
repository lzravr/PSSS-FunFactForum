<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<link rel="stylesheet" href="assets/css/register.css">
<title>Insert title here</title>
</head>
<body class="bg-light">

	<div class="container">
		<div class="py-5 text-center">
			<img class="d-block mx-auto mb-4" src="assets/img/fav.gif" alt=""
				width="72" height="72">
			<h2>Rocket Forum</h2>
		</div>

		<div class="row">

			<div class="col-md-12 order-md-1">
				<h4 class="mb-3">Register</h4>
				<form action="RegisterServlet" method="post"
					class="needs-validation" novalidate>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="firstName">First name</label> <input type="text"
								class="form-control" id="firstName" name="firstname"
								placeholder="" value="" required>
							<div class="invalid-feedback">Valid first name is required.
							</div>
						</div>
						<div class="col-md-6 mb-3">
							<label for="lastName">Last name</label> <input type="text"
								class="form-control" id="lastName" name="lastname"
								placeholder="" value="" required>
							<div class="invalid-feedback">Valid last name is required.
							</div>
						</div>
					</div>

					<div class="mb-3">
						<label for="username">Username</label>
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">@</span>
							</div>
							<input type="text" class="form-control" id="username"
								name="username" placeholder="Username" required>
							<div class="invalid-feedback" style="width: 100%;">Your
								username is required.</div>
						</div>
					</div>

					<div class="mb-3">
						<label for="email">Email <span class="text-muted"></span></label>
						<input type="email" class="form-control" id="email" name="email"
							placeholder="you@example.com" required>
						<div class="invalid-feedback">Please enter a valid email
							address</div>
					</div>

					<div class="mb-3">
						<label for="password">Password <span class="text-muted"></span></label>
						<input type="password" class="form-control" id="password"
							name="password" placeholder="" required>
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

					<button class="btn btn-primary btn-lg btn-block" type="submit">Register
						your account</button>
					<p>
						Already have an account? <a href="index.jsp">Log in</a>
					</p>
					<p class="mt-5 mb-3 text-muted text-center"><a href="help.jsp">Help</a></p>
				</form>
			</div>
		</div>

	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script>
		window.jQuery
				|| document
						.write(
								'<script src="../../../../assets/js/vendor/jquery-slim.min.js"><\/script>')
	</script>
	<script src="../../../../assets/js/vendor/popper.min.js"></script>
	<script src="../../../../dist/js/bootstrap.min.js"></script>
	<script src="../../../../assets/js/vendor/holder.min.js"></script>
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