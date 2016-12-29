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

            <c:import url="admin/admin-header.jsp" />

            <!-- Container for background color -->
            <div class="container-fluid background-container">

                <div class="row row-centered remove-horizontal-margins">

                    <!-- Login box container -->
                    <div class="col-md-4 col-centered login-container box-shadow">

                        <!-- Login box title container -->
                        <div class="login-title-container">
                            <div class="heading-1-1">Team Login</div>
                        </div>

                        <!-- Login form -->
                        <form name="userForm" action="AdministratorLoginServlet" method="post" id="login-form">

                            <!-- Login box credential (email/password) container -->
                            <div class="login-credential-container">

                                <!-- E-mail/Username -->
                                <div class="form-group field-title-1-1">
                                    <input type="text" class="form-control" name="email" maxlength="40" placeholder="E-mail Address" required id="user" />
                                </div>

                                <!-- Password -->
                                <div class="form-group field-title-1-1">
                                    <input type="password" class="form-control" name="password" placeholder="Password" required id="pass" />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Login button -->
                <div class="row row-centered remove-horizontal-margins">
                    <div class="login-button col-md-4 col-centered box-shadow" style="text-align: center !important;" id="loginButton">
                        <button type="submit" class="btn btn-link" style="color: white; width: 100%" form="login-form">Login</button>
                    </div>
                    <p style="color: red; margin-top: 10px;">
                        <c:if test="${error != null}">
                            ${error}
                        </c:if>
                    </p>
                </div>
            </div>

            <c:remove var="error" />

            <c:import url="/footer.html" />
            </body>


        </html>