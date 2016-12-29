<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags must come first in the head; any other head content must come after these tags.
            Scrolling is disabled for this page. -->
<title>Invalid Login</title>

<!-- Page specific CSS -->
<link href="css/login.css" rel="stylesheet">

<c:import url="/header.jsp" />

<div class="background-container">
	<div class="container-fluid login-container">
		<div class="row row-centered"
			style="margin-bottom: 15px; margin-top: 250px;">
			<div class="col-md-4 col-centered">
			<h4 class = "text-center"><b>Invalid Login Details</b></h4>
			<br>
				<form action="ClientLoginServlet" method="post" autocomplete="on">
					<div class="form-group">
						<input type="email" class="form-control" name="email"
							maxlength="40" placeholder="E-mail Address" required />
					</div>
					<div class="form-group">
						<input type="password" class="form-control" name="password"
							placeholder="Password" required />
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-default">Submit</button>
					</div>
			</div>
			</form>
		</div>
	</div>
</div>

<c:import url="/footer.html" />
</body>

</html>
