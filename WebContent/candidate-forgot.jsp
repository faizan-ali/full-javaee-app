<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags. -->
            <title>Reset Password</title>


            <!-- Page specific CSS -->
            <link href="css/login.css" rel="stylesheet">

            <c:import url="/candidate-header.jsp" />


            <!-- Container for background color -->
            <div class="container-fluid background-container">

                <div class="row row-centered">

                    <!-- Reset box container -->
                    <div class="col-md-4 col-centered login-container box-shadow">

                        <!-- Reset box title container -->
                        <div class="login-title-container">
                            <div class="heading-1-1">Reset Password</div>
                        </div>

                        <!-- Reset form -->
                        <form name="userForm" ng-submit="submitForm(userForm.$valid)" novalidate action="ForgotPasswordServlet" method="post" id="login-form">

                            <!-- Reset box credential (email/password) container -->
                            <div class="login-credential-container">

                                <!-- E-mail for reset -->
                                <div class="form-group field-title-1-1">
                                    <input type="email" class="form-control" name="email" maxlength="40" placeholder="E-mail Address" required id="user" />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Submit button -->
                <div class="row row-centered">
                    <div class="login-button col-md-4 col-centered box-shadow" style="text-align: center !important;" id="loginButton">
                        <button type="submit" class="btn btn-link" style="color: white; width: 100%;" form="login-form" onclick="submit();">Submit</button>
                    </div>
                    <p class="body-bold-1-1" style="color: red; margin-top: 10px;">
                        ${error}
                    </p>
                    <c:remove var="error"></c:remove>
                </div>
            </div>

            <c:import url="/footer.html" />
            </body>

        </html>