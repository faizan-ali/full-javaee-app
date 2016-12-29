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
            <title>Candidate Registration</title>

            <!-- Page specific CSS -->
            <link href="css/login.css" rel="stylesheet">

            <c:import url="/candidate-header.jsp" />

            <!-- Form validation script -->
            <script>
                var validationApp = angular.module('validationApp', []);

                // create angular controller
                validationApp.controller('mainController', function ($scope) {

                    // function to submit the form after all validation has occurred            
                    $scope.submitForm = function (isValid) {

                    };

                });
            </script>


            <!-- Container for background image -->
            <div class="background-container">

                <!-- The HTML tags handle significant business logic -->
                <!-- Container for registration form -->
                <div class="container-fluid login-container" ng-app="validationApp" ng-controller="mainController">

                    <div class="row row-centered">

                        <div class="col-md-4 col-centered">

                            <!-- Form -->
                            <form name="userForm" ng-submit="submitForm(userForm.$valid)" novalidate action="CandidateRegisterServlet" method="post">

                                <!-- Business logic error -->
                                <p style="color: red;">
                                    <c:if test="${error != null}">
                                        ${error}
                                    </c:if>
                                </p>

                                <!-- First Name -->
                                <div class="form-group" ng-class="{'has-error':userForm.firstName.$invalid && !userForm.firstName.$pristine}">
                                    <input type="text" class="form-control" name="firstName" maxlength="20" placeholder="First Name" required ng-model="firstName" ng-minlength="2" />
                                    <p ng-show="userForm.firstName.$invalid && !userForm.firstName.$pristine" class="help-block">Please enter your first name.</p>
                                </div>

                                <!-- Last Name -->
                                <div class="form-group" ng-class="{'has-error':userForm.lastName.$invalid && !userForm.lastName.$pristine}">
                                    <input type="text" class="form-control" name="lastName" maxlength="20" placeholder="Last Name" required ng-model="lastName" ng-minlength="2" />
                                    <p ng-show="userForm.lastName.$invalid && !userForm.lastName.$pristine" class="help-block">Please enter your last name.</p>
                                </div>

                                <!-- E-mail -->
                                <div class="form-group" ng-class="{'has-error':userForm.email.$invalid && !userForm.email.$pristine}">
                                    <input type="email" class="form-control" name="email" maxlength="40" placeholder="E-mail Address" required ng-model="email" />
                                    <p ng-show="userForm.email.$invalid && !userForm.email.$pristine" class="help-block">Please enter a valid e-mail address.</p>
                                </div>

                                <!-- Password -->
                                <div class="form-group" ng-class="{'has-error':userForm.password.$invalid && !userForm.password.$pristine}">
                                    <input type="password" class="form-control" name="password" placeholder="Password" minlength="6" required ng-model="password" ng-minlength="4" ng-maxlength="12" />
                                    <p ng-show="userForm.password.$error.minlength" class="help-block">Password is too short.</p>
                                    <p ng-show="userForm.password.$error.maxlength" class="help-block">Password must be less than 13 characters.</p>
                                </div>

                                <!-- City -->
                                <div class="form-group" ng-class="{'has-error':userForm.city.$invalid && !userForm.city.$pristine}">
                                    <input type="text" class="form-control" name="city" maxlength="20" placeholder="City" required ng-model="city" ng-minlength="2" />
                                    <p ng-show="userForm.city.$invalid && !userForm.city.$pristine" class="help-block">Please enter your city.</p>
                                </div>

                                <!-- State -->
                                <div class="form-group">
                                    <select class="form-control" name="state" required>
                                        <option value="State">State</option>
                                        <option value="CA">California</option>
                                        <option value="DC">District of Columbia</option>
                                        <option value="DE">Delaware</option>
                                        <option value="MD">Maryland</option>
                                        <option value="TN">Tennessee</option>
                                        <option value="TX">Texas</option>
                                        <option value="VA">Virginia</option>
                                    </select>
                                </div>

                                <!-- Phone -->
                                <div class="form-group" ng-class="{'has-error':userForm.phone.$invalid && !userForm.phone.$pristine}">
                                    <input class="form-control" type="number" name="phone" maxlength="14" placeholder="Phone Number" required ng-model="phone" ng-minlength="10" ng-pattern="/^((\d{10})|(\d{3}-*\d{3}-*\d{4}))$/" />
                                    <p ng-show="userForm.phone.$invalid && !userForm.phone.$pristine" class="help-block">Please enter your phone number.</p>
                                </div>

                                <c:remove var="error" scope="session" />

                                <!-- Submit Button -->
                                <div class="text-center">
                                    <button type="submit" class="btn btn-default" ng-disabled="userForm.$invalid">Submit</button>
                                </div>
                            </form>
                            <a href="candidate-login.jsp">Back to Candidate Login</a>
                        </div>
                    </div>
                </div>


            </div>
            <c:import url="/footer.html" />
            </body>

        </html>