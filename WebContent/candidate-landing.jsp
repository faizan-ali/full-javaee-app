<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
            <!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
            <html>

            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags.
            Scrolling is disabled for this page. -->
                <title>Welcome to WorkAmerica!</title>

                <!-- Importing header -->
                <c:import url="/candidate-header.jsp" />

                <!-- Masked input validation library -->
                <script src="js/jquery.maskedinput.min.js" type="text/javascript"></script>

                <!-- Datepicker CSS -->
                <link rel="stylesheet" href="css/datepicker.css">

                <!-- Page-specific CSS -->
                <link href="css/login.css" rel="stylesheet">
                <link href="css/candidate-landing.css" rel="stylesheet">

                <!-- Form validation script -->
                <script>
                    var validationApp = angular.module('validationApp', []);
                    // create angular controller
                    validationApp.controller('mainController', function ($scope) {
                        // function to submit the form after all validation has occurred
                        $scope.submitForm = function (isValid) {};
                    });
                </script>

                <!-- In Platform Desktop Candidate Navbar -->
                <div class="hidden-xs hidden-sm container-fluid candidate-nav-desktop-container">
                    <div class="row candidate-nav-desktop">
                        <div class="col-lg-5 col-md-6 col-xs-12" id="navbar-view-message">
                            <div class="col-md-1 col-xs-1 icon-eye" style="font-size: 20px; color: white;"></div>
                            <div class="col-md-10 col-xs-10 remove-horizontal-padding-mobile subhead-1-1" style="color:white !important;" >You are currently viewing your profile</div>
                        </div>
                        <div class="col-lg-5 col-md-6 hidden" id="navbar-edit-message">
                            <div class="col-md-1 col-xs-1 icon-note" style="font-size: 20px; color: white;"></div>
                            <div class="col-md-10 col-xs-10 remove-horizontal-padding-mobile subhead-1-1" style="color:white !important;">You are currently editing your profile</div>
                        </div>

                        <div class="col-md-6 pull-right platform-nav-back" style="margin-top:0px;">
                            <div class="icon-settings col-md-1" style="font-size: 20px; color:white;"></div>
                            <div class="subhead-1-1 col-lg-4 col-md-5">
                                <a href="candidate-account.jsp" style="color:white !important;">Account Settings</a>
                            </div>
                            <div class="icon-logout col-md-1 col-md-offset-1" style="font-size: 20px; color:white;"></div>
                            <div class="subhead-1-1 col-lg-5 col-md-4">
                                <form action="LogoutServlet">
                                    <button type="submit" class="btn btn-link remove-horizontal-padding remove-vertical-padding subhead-1-1" style="color:white !important;">Logout</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- In Platform Mobile Candidate Navbar -->
                <div class="visible-xs visible-sm container candidate-nav-mobile-container navbar-fixed-bottom">
                    <div class="row row-centered candidate-nav-mobile">
                        <div class="col-xs-2 col-centered navbar-button" onClick="viewProfile();">
                            <div class="row text-center">
                                <div class="icon-user"></div>
                            </div>
                            <div class="row text-center navbar-text">View
                            </div>
                        </div>

                        <div class="col-xs-2 col-centered navbar-button" onClick="editProfile();">
                            <div class="row text-center">
                                <div class="icon-note"></div>
                            </div>
                            <div class="row text-center navbar-text">Edit
                            </div>
                        </div>

                        <div class="col-xs-2 col-centered navbar-button" onClick="viewJobs();">
                            <div class="row text-center">
                                <div class="icon-magnifier"></div>
                            </div>
                            <div class="row text-center navbar-text">Jobs
                            </div>
                        </div>

                        <div class="col-xs-2 col-centered navbar-button" onClick="settings();">
                            <div class="row text-center">
                                <div class="icon-settings"></div>
                            </div>
                            <div class="row text-center navbar-text">Settings</div>
                        </div>

                        <div class="col-xs-2 col-centered navbar-button" onClick="viewHelp();">
                            <div class="row text-center">
                                <div class="icon-question"></div>
                            </div>
                            <div class="row text-center navbar-text">Help</div>
                        </div>

                        <div class="col-xs-2 col-centered navbar-button">
                            <div class="row text-center">
                                <div class="icon-logout"></div>
                            </div>
                            <div class="row text-center navbar-text">Logout
                            </div>
                        </div>

                    </div>
                </div>

                <div class="candidate-profile-background-container">
                    <div class="container candidate-profile-marginator">
                        <div class="row candidate-profile-container">
                            <!-- Container for account settings form in mobile -->
                            <c:import url="/candidate/candidate-account-mobile.jsp" />

                            <!-- Welcome box and jobs container -->
                            <c:import url="/candidate/candidate-welcome-jobs.jsp" />

                            <!-- Containers for profile summary card and details (view) -->
                            <c:import url="/candidate/candidate-profile-view.jsp" />

                            <!-- Containers for profile summary card and details (edit) -->
                            <c:import url="/candidate/candidate-profile-edit.jsp" />

                        </div>
                    </div>
                </div>


                <!-- Page specific JS -->
                <script src="js/candidate-scripts.js"></script>

                <!-- Datepicker JS -->
                <script src="js/bootstrap-datepicker.js" type="text/javascript"></script>

                <!-- Jobs ticker -->
                <script src="js/jquery.newsTicker.js" type="text/javascript"></script>
                <script>
                    $('#job-list').newsTicker({
                        row_height: 100,
                        max_rows: 3,
                        prevButton: $('#job-list-prev'),
                        nextButton: $('#job-list-next')
                    });
                </script>

                <!-- Datepicker Initialization -->
                <script>
                    $('#date').datepicker();
                </script>

                <script>
                    $(document).ready(
                            initializeSelect2();
                    );
                </script>


                <c:remove var="resumeError" />
                <c:remove var="resumeSuccess" />
                </body>

            </html>