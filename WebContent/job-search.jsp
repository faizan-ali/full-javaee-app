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
                <title>Job Search</title>

                <!-- Page specific CSS -->
                <link href="css/job-search.css" rel="stylesheet">

                <!-- Page specific JS -->
                <script src="js/job-board-scripts.js"></script>

                <c:import url="/header.jsp" />

                <!-- Landing page container -->
                <div class="container background-container">
                    <!-- Form for search -->
                    <form action="JobSearchServlet">

                        <div class="row row-centered" id="logo-container">
                            <div class="col-xs-4 col-centered">
                                <img src="img/high-res-logo.png" alt="WorkAmerica Logo" style="width: 100%;">
                            </div>
                        </div>

                        <!-- Keyword search boxes -->

                        <div class="row row-centered body-1-1">
                            <div class="col-xs-4 col-centered">
                                <div class="form-group box-shadow">
                                    <input type="text" class="form-control" placeholder="Job Title, Keywords or Company" name="keywords" />
                                </div>
                            </div>
                        </div>

                        <div class="row row-centered body-1-1">
                            <div class="col-xs-4 col-centered">
                                <div class="form-group box-shadow">
                                    <input type="text" class="form-control" placeholder="City, State or Zip Code" name="location" />
                                </div>
                            </div>
                        </div>

                        <div class="row row-centered">
                            <div class="col-md-2 col-centered">
                                <button type="submit" class="btn job-search-button body-bold-1-2">SEARCH</button>
                            </div>
                        </div>
                    </form>
                </div>

                <input type="hidden" name="jobType" value="">


                <c:import url="/footer.html" />

                <script>
                    window.onload = function () {
                        initializeSelect2("#fieldsList", "#schoolsList", "#certificationsList", "#statesList");
                    }
                </script>
                </body>

            </html>