<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

            <div class="hidden" id="account-container">
                <div class="container-fluid background-container remove-horizontal-padding" ng-app="validationApp" ng-controller="mainController">

                    <div class="row row-centered remove-horizontal-margins">

                        <!-- Settings box container -->
                        <div class="col-md-4 col-centered box-shadow remove-horizontal-padding" id="candidate-login-container">

                            <!-- Settings box title container -->
                            <div class="login-title-container heading-1-1 text-center">
                                Settings
                            </div>

                            <!-- Settings Form -->
                            <form name="userForm" ng-submit="submitForm(userForm.$valid)" novalidate action="CandidateAccountServlet" method="post" id="login-form">

                                <!-- Credential(email/password) container -->
                                <div class="login-credential-container">

                                    <!-- E-mail/Username -->
                                    <div class="form-group field-title-1-1" ng-class="{'has-error':userForm.email.$invalid && !userForm.email.$pristine}">
                                        <div class="body-bold-1-1">Update e-mail address</div>
                                        <input type="email" class="form-control" name="email" maxlength="40" placeholder="E-mail Address" required ng-model="email" ng-init="email='${user.email}'" id="user" class="input-clean" />
                                        <p ng-show="userForm.email.$invalid && !userForm.email.$pristine" class="help-block">Please enter a valid e-mail address</p>
                                    </div>

                                    <!-- Password -->
                                    <div class="form-group field-title-1-1" ng-class="{'has-error':userForm.password.$invalid && !userForm.email.$pristine}">
                                        <div class="body-bold-1-1">Update password</div>
                                        <input type="password" class="form-control" name="password" placeholder="Password" required ng-model="password" ng-minlength="4" ng-maxlength="12" id="pass" />
                                        <p ng-show="userForm.password.$error.minlength" class="help-block">Password is too short</p>
                                        <p ng-show="userForm.password.$error.maxlength" class="help-block">Password must be less than 13 characters</p>
                                    </div>

                                    <!-- Profile visibility -->
                                    <div class="form-group field-title-1-1">
                                        <div class="body-bold-1-1">Profile visibility</div>
                                        <div class="radio">
                                            <label class="body-1-1">
                                                <c:choose>
                                                    <c:when test="${user.authorized eq 'Yes'}">
                                                        <input type="radio" name="authorized" value="Yes" checked />On- Allow employers to find my profile when searching for candidates
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="radio" name="authorized" value="Yes" />On- Allow employers to find my profile when searching for candidates
                                                    </c:otherwise>
                                                </c:choose>
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label class="body-1-1">
                                                <c:choose>
                                                    <c:when test="${user.authorized eq 'No'}">
                                                        <input type="radio" name="authorized" value="No" checked/>Off- Employers will not be be able to find my profile when searching for candidates
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="radio" name="authorized" value="No" />Off- Employers will not be be able to find my profile when searching for candidates
                                                    </c:otherwise>
                                                </c:choose>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>

                    <!-- Submit button -->
                    <div class="row row-centered remove-horizontal-margins">
                        <div class="login-button col-md-4 col-centered box-shadow" style="text-align: center !important;" id="candidateLoginButton">
                            <button type="submit" class="btn btn-link" ng-disabled="userForm.$invalid" style="color: white; width: 100%; text-align: center;" form="login-form" onclick="submitForm();">Submit Changes</button>
                        </div>
                    </div>
                </div>
            </div>