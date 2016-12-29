<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags. -->
            <title>Login</title>


            <!-- Page specific CSS -->
            <link href="css/login.css" rel="stylesheet">

            <c:import url="/header.jsp" />

            <!-- Container for background color -->
            <div class="container-fluid background-container">

                <div class="row row-centered remove-horizontal-margins remove-horizontal-padding">

                    <!-- Login box container -->
                    <div class="col-md-4 login-container col-centered box-shadow remove-horizontal-padding">

                        <!-- Login box title container -->
                        <div class="login-title-container">
                            <button onclick="login();" class="heading-1-1 btn btn-link" id="loginSwitch">Employer Login</button>
                            <div class="heading-1-1" style="color: #C4CC7A"> / </div>
                            <button onclick="register();" class="heading-1-2 btn btn-link" id="signupSwitch">Register</button>
                        </div>

                        <!-- Signup Form -->
                        <div id="client-signup-form" class="hidden">
                            <form action="ClientRegisterServlet" id="signup-form" method="post">

                                <!-- First Name -->
                                <div class="form-group">
                                    <div class="col-md-6">
                                        <div class="field-title-1-1">First Name</div>
                                        <input type="text" value="${firstName}" name="firstName" class="body-1-1 input-clean form-control" id="signUpFirstName" required />
                                    </div>
                                </div>

                                <!-- Last Name -->
                                <div class="form-group">
                                    <div class="col-md-6">
                                        <div class="field-title-1-1">Last Name</div>
                                        <input type="text" value="${lastName}" name="lastName" id="signUpLastName" class="body-1-1 input-clean" required/>
                                    </div>
                                </div>

                                <!-- Email -->
                                <div class="form-group">
                                    <div class="col-md-6">
                                        <div class="field-title-1-1">Email Address</div>
                                        <input type="text" class="form-control body-1-1 input-clean" name="email" pattern="^[^\s@]+@[^\s@]+\.[^\s@]{2,}$" value="${email}" maxlength="40" title="Please enter a valid e-mail address" id="signUpEmail" required />
                                    </div>
                                </div>

                                <!-- Company -->
                                <div class="form-group">
                                    <div class="col-md-6">
                                        <div class="field-title-1-1">Company</div>
                                        <input type="text" class="form-control body-1-1 input-clean" name="company" value="${company}" maxlength="40" title="" id="company" required />
                                    </div>
                                </div>

                                <!-- Password -->
                                <div class="form-group">
                                    <div class="col-md-6">
                                        <div class="field-title-1-1">Password</div>
                                        <input type="password" class="form-control body-1-1 input-clean" name="password" pattern=".{4,}" title="A minimum of four characters is required" id="signUpPassword" placeholder="Minimum of 4 characters" required/>
                                    </div>

                                    <!-- Repeat password -->
                                    <div class="col-md-6">
                                        <div class="field-title-1-1">Repeat Password</div>
                                        <input type="password" class="form-control body-1-1 input-clean" name="repeatPassword" pattern=".{4,}" title="A minimum of four characters is required" placeholder="Minimum of 4 characters" id="signUpRepeatPassword" required/>
                                    </div>
                                </div>

                                <!-- Code -->
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="field-title-1-1">Referral Code (if given)</div>
                                        <input type="text" class="form-control body-1-1 input-clean" name="code" maxlength="40" title="" id="code" />
                                    </div>
                                </div>
                            </form>
                        </div>

                        <!-- Login form -->
                        <div>
                            <form name="userForm" action="login-servlet" method="post" id="login-form">

                                <!-- Login box credential (email/password) container -->
                                <div class="login-credential-container">

                                    <!-- E-mail/Username -->
                                    <div class="form-group field-title-1-1">
                                        <input type="email" class="form-control" name="email" maxlength="40" placeholder="E-mail Address" required id="user" />
                                    </div>

                                    <!-- Password -->
                                    <div class="form-group field-title-1-1">
                                        <input type="password" class="form-control" name="password" placeholder="Password" required id="pass" />
                                    </div>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>


                <div class="row row-centered remove-horizontal-margins">
                    <!-- Login button -->
                    <div class="login-button col-md-4 col-centered box-shadow" style="text-align: center !important;" id="loginButton">
                        <button type="submit" class="btn btn-link" style="color: white; width: 100%" form="login-form" onclick="submitForm('login');">Login</button>
                    </div>

                    <!-- Signup button -->
                    <div class="login-button col-md-4 col-centered box-shadow hidden" style="text-align: center !important;" id="signupButton">
                        <button type="submit" class="btn btn-link" style="color: white; width: 100%" form="signup-form" onclick="submitForm('signup');">Submit</button>
                    </div>

                    <p class="body-bold-1-1" style="color: red; margin-top: 10px;">
                        ${signupError}
                    </p>
                    <p class="body-bold-1-1" style="color: red; margin-top: 10px;">
                        ${error}
                    </p>
                    <input type="hidden" value="${signupError}" id="signupError">
                </div>
            </div>

            <c:import url="/footer.html" />

            <script>
                function register() {
                    $("#login-form").toggleClass("hidden");
                    $("#client-signup-form").toggleClass("hidden");
                    $("#login-container").toggleClass("remove-horizontal-padding");
                    $("#loginSwitch").removeClass("heading-1-1");
                    $("#loginSwitch").addClass("heading-1-2");
                    $("#signupSwitch").removeClass("heading-1-2");
                    $("#signupSwitch").addClass("heading-1-1");
                    $("#loginButton").addClass("hidden");
                    $("#signupButton").removeClass("hidden");
                }
            </script>

            <script>
                function login() {
                    $("#login-form").toggleClass("hidden");
                    $("#client-signup-form").toggleClass("hidden");
                    $("#login-container").toggleClass("remove-horizontal-padding");
                    $("#loginSwitch").removeClass("heading-1-2");
                    $("#loginSwitch").addClass("heading-1-1");
                    $("#signupSwitch").removeClass("heading-1-1");
                    $("#signupSwitch").addClass("heading-1-2");
                    $("#signupButton").addClass("hidden");
                    $("#loginButton").removeClass("hidden");
                }
            </script>

            <script>
                if (document.getElementById("signupError").value.length > 0) {
                    register();
                }
            </script>

            <script>
                function getParam(param) {
                    var vars = {};
                    window.location.href.replace(location.hash, '').replace(
                        /[?&]+([^=&]+)=?([^&]*)?/gi, // regexp
                        function (m, key, value) { // callback
                            vars[key] = value !== undefined ? value : '';
                        }
                    );

                    if (param) {
                        return vars[param] ? vars[param] : null;
                    }
                    return vars;
                }
            </script>

            <script>
                var reg = getParam('register');
                if (reg == 'yes') {
                    register();
                }
            </script>

            <script>
                function submitForm(type) {
                    if (type == 'login') {
                        $('#login-form').submit();
                    } else {
                        $('#signup-form').submit();
                    }
                }
            </script>

            <c:remove var="error"></c:remove>
            <c:remove var="firstName"></c:remove>
            <c:remove var="lastName"></c:remove>
            <c:remove var="email"></c:remove>
            <c:remove var="signupError"></c:remove>

            </body>

        </html>