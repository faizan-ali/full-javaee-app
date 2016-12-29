<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
                <title>Login</title>

                <!-- Page specific CSS -->
                <link href="css/login.css" rel="stylesheet">
                <c:import url="/header.jsp" />

                <div class="background-container">
                    <div class="container" style="height: 380px;">
                        <a href="team.jsp" class="pull-right"> Admin Login </a>
                        <div class="row row-centered" style="margin-top: 150px; margin-bottom: 10px;">
                            <div class="col-md-8 col-centered">
                                <a href="login.jsp">
                                    <button type="button" class="btn btn-lg btn-default">Employer Login</button>
                                </a>
                                <a href="candidate-login.jsp">
                                    <button type="button" class="btn btn-lg btn-default">Candidate Login</button>
                                </a>
                                <a href="job-search.jsp">
                                    <button type="button" class="btn btn-lg btn-default">Job Search</button>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <c:import url="/footer.html" />
                </body>

            </html>