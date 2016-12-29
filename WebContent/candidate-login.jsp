<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags. -->
            <title>Login</title>


            <!-- Page specific CSS -->
            <link href="css/login.css" rel="stylesheet">

            <meta name="description" content="WorkAmerica">
            <meta name="author" content="Faizan Ali">
            <link rel="icon" href="favicon.ico">

            <!-- Bootstrap core CSS -->
            <link href="css/bootstrap.min.css" rel="stylesheet">

            <!-- Site-wide custom CSS -->
            <link href="css/site-wide.css" rel="stylesheet">

            <!-- jQuery Library -->
            <script src="js/jquery-1.12.0.min.js"></script>

            <!-- Bootstrap core JavaScript -->
            <script type="text/javascript" src="js/bootstrap.min.js"></script>

            <!-- Simple-Icons Font -->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.2.3/css/simple-line-icons.css">

            <!-- Select2 JS Library and CSS -->
            <link href="css/select2.min.css" rel="stylesheet" />
            <script src="js/select2.min.js"></script>

            <script src='https://cdn.slaask.com/chat.js'></script>
            <script>
                _slaask.init('0b75a46e0da9477b6cdaeafd34d8ce2e');
            </script>

            <script>
                (function (i, s, o, g, r, a, m) {
                    i['GoogleAnalyticsObject'] = r;
                    i[r] = i[r] || function () {
                        (i[r].q = i[r].q || []).push(arguments)
                    }, i[r].l = 1 * new Date();
                    a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0];
                    a.async = 1;
                    a.src = g;
                    m.parentNode.insertBefore(a, m)
                })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

                ga('create', 'UA-38337436-2', 'auto');
                ga('require', 'linkid');
                ga('send', 'pageview');
            </script>

            <!-- Gets device/browser/OS info -->
            <script type="text/javascript" src="https://s3-us-west-2.amazonaws.com/workamerica-public/js/ua-parser.js"></script>

        </head>



        <body>
            <div id="wrapper">
                <!-- Navigation bar -->
                <nav class="navbar navbar-default" role="navigation">
                    <div class="container-fluid hidden-xs hidden-sm">
                        <div class="navbar-header">
                            <!-- WorkAmerica Logo -->
                            <a class="navbar-brand" href="http://www.workamerica.co">
                                <img class="img-responsive" src="img/LOGO.png" style="margin-left: 10px;" width="180" height="30">
                            </a>
                        </div>
                    </div>
                    <div class="container visible-xs visible-sm">
                        <div class="navbar-header">
                            <!-- WorkAmerica Logo -->
                            <img class="img-responsive" src="img/workamerica.png" style="margin: 0 auto; margin-top: 15px;" width="180">
                        </div>
                    </div>
                </nav>

                <!-- Container for background color -->
                <div class="container-fluid background-container">

                    <div class="row row-centered remove-horizontal-margins remove-horizontal-padding">

                        <!-- Login box container -->
                        <div class="col-md-4 login-container col-centered box-shadow remove-horizontal-padding">

                            <!-- Login box title container -->
                            <div class="login-title-container">
                                <button onclick="candidateLogin();" class="heading-1-1 btn btn-link" id="loginSwitch">Candidate Login</button>
                                <div class="heading-1-1" style="color: #C4CC7A"> / </div>
                                <button onclick="candidateSignup();" class="heading-1-2 btn btn-link" id="signupSwitch">Sign Up</button>
                            </div>

                            <!-- Signup Form -->
                            <div id="candidate-signup-form" class="hidden">
                                <a href="FacebookSignupServlet" id="facebookImage"><img src="/img/signupfacebook.png" alt="Sign In with Facebook"></a>

                                <div class="archer text-center" id="or-text">-or-</div>

                                <form action="CandidateRegisterServlet" id="signup-form" method="post">

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
                                        <div class="col-md-12">
                                            <div class="field-title-1-1">Email Address</div>
                                            <input type="text" class="form-control body-1-1 input-clean" name="email" pattern="^[^\s@]+@[^\s@]+\.[^\s@]{2,}$" value="${email}" maxlength="40" title="Please enter a valid e-mail address" id="signUpEmail" required />
                                        </div>

                                    </div>

                                    <!-- Password -->
                                    <div class="form-group">
                                        <div class="col-md-6" style="margin-bottom: 15px;">
                                            <div class="field-title-1-1">Password</div>
                                            <input type="password" class="form-control body-1-1 input-clean" name="password" pattern=".{4,}" title="A minimum of four characters is required" id="signUpPassword" placeholder="Minimum of 4 characters" required/>
                                        </div>

                                        <!-- Repeat password -->
                                        <div class="col-md-6" style="margin-bottom:15px;">
                                            <div class="field-title-1-1">Repeat Password</div>
                                            <input type="password" class="form-control body-1-1 input-clean" name="repeatPassword" pattern=".{4,}" title="A minimum of four characters is required" placeholder="Minimum of 4 characters" id="signUpRepeatPassword" required/>
                                        </div>
                                    </div>

                                    <!-- Referral -->
                                    <input type="hidden" value="" name="referral" id="ref">

                                    <!-- Device/Browser/OS info -->
                                    <input type="hidden" value="" name="browser">
                                    <input type="hidden" value="" name="browserVersion">
                                    <input type="hidden" value="" name="device">
                                    <input type="hidden" value="" name="deviceType">
                                    <input type="hidden" value="" name="deviceVendor">
                                    <input type="hidden" value="" name="os">
                                    <input type="hidden" value="" name="osVersion">

                                </form>
                            </div>



                            <!-- Login Form -->
                            <div class="">
                                <form name="userForm" action="login-servlet" method="post" id="login-form">

                                    <!-- Login box credential (email/password) container -->
                                    <div class="login-credential-container">

                                        <!-- E-mail/Username -->
                                        <div class="form-group field-title-1-1">
                                            <input type="text" class="form-control" name="email" maxlength="40" placeholder="E-mail Address or Phone" required id="user" />
                                        </div>

                                        <!-- Password -->
                                        <div class="form-group field-title-1-1">
                                            <input type="password" class="form-control" name="password" placeholder="Password" required minlength="4" id="pass" />

                                        </div>

                                        <div class="row">
                                            <div class="col-md-3 pull-right"> <a class="field-title-1-4" href="candidate-forgot.jsp">Forgot Password?</a></div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="row row-centered remove-horizontal-margins">
                        <!-- Login button -->
                        <div class="login-button col-md-4 col-centered box-shadow" style="text-align: center !important;" id="candidateLoginButton">
                            <button type="submit" class="btn btn-link" style="color: white; width: 100%" form="login-form" onclick="submitForm('login');">Login</button>
                        </div>

                        <!-- Signup button -->
                        <div class="login-button col-md-4 col-centered box-shadow hidden" style="text-align: center !important;" id="candidateSignupButton">
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
                    function candidateSignup() {
                        $("#login-form").toggleClass("hidden");
                        $("#candidate-signup-form").toggleClass("hidden");
                        $("#candidate-login-container").toggleClass("remove-horizontal-padding");
                        $("#loginSwitch").removeClass("heading-1-1");
                        $("#loginSwitch").addClass("heading-1-2");
                        $("#signupSwitch").removeClass("heading-1-2");
                        $("#signupSwitch").addClass("heading-1-1");
                        $("#candidateLoginButton").addClass("hidden");
                        $("#candidateSignupButton").removeClass("hidden");
                    }

                    function candidateLogin() {
                        $("#login-form").toggleClass("hidden");
                        $("#candidate-signup-form").toggleClass("hidden");
                        $("#candidate-login-container").toggleClass("remove-horizontal-padding");
                        $("#loginSwitch").removeClass("heading-1-2");
                        $("#loginSwitch").addClass("heading-1-1");
                        $("#signupSwitch").removeClass("heading-1-1");
                        $("#signupSwitch").addClass("heading-1-2");
                        $("#candidateSignupButton").addClass("hidden");
                        $("#candidateLoginButton").removeClass("hidden");
                    }
                </script>

                <script>
                    if (document.getElementById("signupError").value.length > 0) {
                        candidateSignup();
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
                    var signUp = getParam('signup');
                    var ref = getParam('ref');
                    if (signUp == 'yes') {
                        candidateSignup();
                    }
                    if (ref) {
                        $('#ref')[0].setAttribute("value", ref);
                    }
                </script>

                <script>
                    function submitForm(type) {

                        if (type == 'login') {
                            if (document.getElementById('user') && document.getElementById('pass')) {
                                $('#login-form').submit();
                            }
                        } else {
                            if (document.getElementById('signUpFirstName') && document.getElementById('signUpLastName') &&
                                document.getElementById('signUpEmail') && document.getElementById('signUpPassword') &&
                                document.getElementById('signUpRepeatPassword')) {

                                var parser = new UAParser().getResult();
                                var browser = parser.browser.name;
                                var browserVersion = parser.browser.version;
                                var device = parser.device.model;
                                var deviceType = parser.device.type;
                                var deviceVendor = parser.device.vendor;
                                var os = parser.os.name;
                                var osVersion = parser.os.version;

                                $('input[name="os"]').val(os);
                                $('input[name="osVersion"]').val(osVersion);
                                $('input[name="device"]').val(device);
                                $('input[name="deviceType"]').val(deviceType);
                                $('input[name="deviceVendor"]').val(deviceVendor);
                                $('input[name="browser"]').val(browser);
                                $('input[name="browserVersion"]').val(browserVersion);

                                $('#signup-form').submit();
                            }
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