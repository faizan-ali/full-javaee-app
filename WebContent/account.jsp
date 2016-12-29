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
            <title>Your Account Settings</title>

            <c:import url="/header.jsp" />

            <!-- Page-specific CSS -->
            <link href="css/landing.css" rel="stylesheet">
            <link href="css/login.css" rel="stylesheet">

            <!-- Form validation script -->
            <script>
                var validationApp = angular.module('validationApp', []);
                // create angular controller
                validationApp.controller('mainController', function ($scope) {
                    // function to submit the form after all validation has occurred            
                    $scope.submitForm = function (isValid) {};
                });
            </script>




            <div class="container-fluid platform-nav-container">
                <div class="row platform-nav">

                    <!-- Desktop nav, hidden from mobile -->
                    <div class="hidden-xs hidden-sm">
                        <div class="col-md-4 platform-nav-back">
                            <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:white;"></div>
                            <div class="subhead-1-3 col-md-9">
                                <a href="landing.jsp" style="color:white;">Back To Candidate Search</a>
                            </div>
                        </div>
                        <div class="col-md-3 col-md-offset-4 platform-nav-button" id="navbar-view-message">
                            <div class="col-md-1 icon-settings" style="font-size: 20px; color: white"></div>
                            <div class="col-md-10 subhead-1-1" style="color:white;">Account Settings</div>
                        </div>
                    </div>


                    <!-- Mobile Nav -->
                    <div class="visible-xs visible-sm">
                        <div class="col-xs-12 platform-nav-back text-center">
                            <div class="icon-arrow-left-circle col-xs-1" style="font-size: 20px; color:white;"></div>
                            <div class="subhead-1-3 col-xs-9">
                                <a href="landing.jsp">Back To Candidate Search</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <!-- Container for account settings form -->
            <div class="container-fluid background-container" ng-app="validationApp" ng-controller="mainController">

                <div class="row row-centered remove-horizontal-margins">

                    <!-- Settings box container -->
                    <div class="col-md-4 col-centered box-shadow remove-horizontal-padding" id="candidate-login-container">

                        <!-- Settings box title container -->
                        <div class="login-title-container heading-1-1 text-center">
                            Settings
                        </div>

                        <!-- Settings Form -->
                        <form name="userForm" ng-submit="submitForm(userForm.$valid)" novalidate action="ClientAccountServlet" method="post" id="account-form">

                            <!-- Credential(email/password) container -->
                            <div class="login-credential-container">

                                <!-- First Name -->
                                <div class="form-group field-title-1-1" ng-class="{'has-error':userForm.firstName.$invalid && !userForm.firstName.$pristine}">
                                    <input type="text" class="form-control" name="firstName" maxlength="40" placeholder="First Name" required ng-model="firstName" ng-init="firstName='${user.firstName}'" />
                                    <p ng-show="userForm.firstName.$invalid && !userForm.firstName.$pristine" class="help-block">Please enter your first name</p>
                                </div>

                                <!-- Last Name -->
                                <div class="form-group field-title-1-1" ng-class="{'has-error':userForm.lastName.$invalid && !userForm.lastName.$pristine}">
                                    <input type="text" class="form-control" name="lastName" maxlength="40" placeholder="Last Name" required ng-model="lastName" ng-init="lastName='${user.lastName}'" />
                                    <p ng-show="userForm.lastName.$invalid && !userForm.lastName.$pristine" class="help-block">Please enter your last name</p>
                                </div>

                                <!-- E-mail/Username -->
                                <div class="form-group field-title-1-1">
                                    <input type="email" class="form-control" name="email" maxlength="40" placeholder="E-mail Address" value="${user.email}" disabled/>
                                </div>

                                <!-- Password -->
                                <div class="form-group field-title-1-1" ng-class="{'has-error':userForm.password.$invalid && !userForm.email.$pristine}">
                                    <input type="password" class="form-control" name="password" placeholder="Password" required ng-model="password" ng-minlength="4" ng-maxlength="12" id="pass" />
                                    <p ng-show="userForm.password.$error.minlength" class="help-block">Password is too short</p>
                                    <p ng-show="userForm.password.$error.maxlength" class="help-block">Password must be less than 13 characters</p>
                                </div>

                            </div>
                        </form>

                    </div>
                </div>

                <!-- Submit button -->
                <div class="row row-centered remove-horizontal-margins">
                    <div class="login-button col-md-4 col-centered box-shadow" style="text-align: center !important;" id="loginButton">
                        <button type="submit" class="btn btn-link" ng-disabled="userForm.$invalid" style="color: white; width: 100%" form="account-form" onclick="submitForm();">Submit Changes</button>
                    </div>
                </div>
            </div>

            <style>
                @media (max-device-width: 736px) {
                    .login-button {
                        width: 330px !important;
                    }
                }
            </style>

            <script>
                function submitForm() {
                    $('#account-form').submit();
                }
            </script>

            <c:import url="/footer.html" />
            </body>

        </html>