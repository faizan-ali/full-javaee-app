<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <body>
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags.
Scrolling is disabled for this page. -->
            <title>WorkAmericaTalentPipeline</title>

            <c:import url="/header.jsp" />

            <!-- Page-specific CSS -->
            <link href="css/landing.css" rel="stylesheet">


            <div class="container-fluid platform-nav-container">
                <div class="row platform-nav">

                    <!-- Desktop nav, hidden from mobile -->
                    <div class="hidden-xs hidden-sm">
                        <div class="col-lg-5 col-md-6 col-sm-6 platform-nav-back">
                            <div class="icon-settings col-md-1" style="font-size: 20px; color:white;"></div>
                            <div class="subhead-1-1 col-md-5">
                                <a href="account.jsp" style="color: white;">Account Settings</a>
                            </div>
                            <div class="icon-logout col-md-1 col-md-offset-1" style="font-size: 20px; color:white;"></div>
                            <div class="col-md-4">
                                <form action="LogoutServlet">
                                    <button type="submit" class="btn btn-link subhead-1-1 remove-horizontal-padding remove-vertical-padding">Logout</button>
                                </form>
                            </div>
                        </div>
                        <div class="col-lg-2 col-md-3 col-sm-3 text-center platform-nav-button subhead-1-2 pull-right" style = "color:white !important"><a href="pipeline.jsp">Candidate Pipeline</a></div>
                        <div class="col-lg-2 col-md-3 col-sm-3 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right" style = "color:white !important">Search Candidates</div>
                    </div>

                    <!-- Mobile Nav -->
                    <div class="visible-xs visible-sm">
                        <div class="col-xs-12 text-center" style="margin-top: 8px;">
                            <div class="icon-settings col-xs-1" style="font-size: 14px; color:white;"></div>
                            <div class="subhead-1-1 col-xs-2">
                                <a href="account.jsp" style="color: white; font-size: 11px;">Settings</a>
                            </div>
                            <div class="icon-logout col-xs-1 col-xs-offset-4" style="font-size: 14px; color:white;"></div>
                            <div class="col-xs-2" style="font-size: 11px !important;">
                                <form action="LogoutServlet">
                                    <button type="submit" class="btn btn-link subhead-1-1 remove-horizontal-padding remove-vertical-padding" style="font-size: 11px !important;">Logout</button>
                                </form>
                            </div>
                            <div class="col-xs-6 platform-nav-button subhead-1-2 platform-nav-button-border" style = "color:white !important">Search Candidates</div>
                            <div class="col-xs-6 platform-nav-button subhead-1-2">
                                <a href="pipeline.jsp" style = "color:white !important">Candidate Pipeline</a></div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Landing page container -->
            <div class="container-fluid background-container" style="min-height: 550px;">
                <!-- Form for search -->
                <form action="SearchServlet">

                    <!-- Keyword search box container -->
                    <div class="row box-shadow body-1-1 keyword-search-container">
                        <div class="col-lg-12 col-md-12">

                            <!-- Keyword search box -->
                            <div class="keyword-search">

                                <!-- Keyword search field -->
                                <div class="input-group col-md-12">
                                    <input type="text" class="search-query form-control" placeholder="Keyword Search" name="keywords" />

                                    <!-- Search icon -->
                                    <span class="input-group-btn">
                                       <button type = "button" class = "btn">
                                        <span class="glyphicon glyphicon-search"></span>
                                    </button>
                                    </span>
                                </div>

                            </div>
                        </div>
                    </div>

                    <!-- Search criteria boxes container -->
                    <div class="row row-centered criteria-container">

                        <!-- Container for Field of Study -->
                        <div class="col-md-3 col-sm-12 col-xs-12 col-centered select-container box-shadow" id="fields-container">
                            <div class="criteria-title field-title-1-1">Field of Study</div>
                            <select data-placeholder="Please choose" class="multiple-select body-1-1" name="fields" id="fieldsList" multiple="multiple">
                                <option value="All">All</option>
                                <c:forEach items="${fieldList}" var="field">
                                    <option value="${field.name}">${field.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-3 col-sm-12 col-xs-12 col-centered select-container box-shadow" id="certifications-container">
                            <div class="criteria-title field-title-1-1">Certifications</div>
                            <select data-placeholder="Please choose" class="multiple-select body-1-1" name="certifications" id="certificationsList" multiple="multiple">
                                <option value="All">All</option>
                                <c:forEach items="${certificationList}" var="cert">
                                    <option value="${cert.certification}">${cert.certification}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!--
                            <div class="col-md-2 col-sm-12 col-xs-12 col-centered select-container box-shadow" id="states-container">
                                <div class="criteria-title field-title-1-1">State</div>
                                <select data-placeholder="Please choose" class="multiple-select body-1-1" name="states" id="statesList" multiple="multiple">
                                    <option value="All">All</option>
                                    <c:forEach items="${stateList}" var="state">
                                        <option></option>
                                        <option value="${state.abbreviation}">${state.abbreviation}</option>
                                    </c:forEach>
                                </select>

                            </div> -->

                        <div class="col-md-3 col-sm-12 col-xs-12 col-centered select-container box-shadow" id="schools-container">
                            <div class="criteria-title field-title-1-1">School</div>
                            <select data-placeholder="Please choose" class="multiple-select body-1-1" name="schools" id="schoolsList" multiple="multiple">
                                <option value="All">All</option>
                                <c:forEach items="${schoolList}" var="school">
                                    <c:if test="${school.name ne 'Other' or school.name ne 'Not currently Enrolled'}">
                                        <option value="${school.name}">${school.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row row-centered body-bold-1-1 radius-container">
                        <div class="col-md-7 col-centered row-centered keyword-search ">
                            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 col-centered">Search within</div>

                            <div class="box-shadow col-lg-1 col-md-2 col-sm-2 col-xs-8 col-centered">
                                <input type="number" placeholder="miles" class="" name="radiusDistance">
                            </div>
                            <div class="col-lg-1 col-md-2 col-sm-2 col-xs-2 col-centered">of</div>
                            <div class="box-shadow col-lg-1 col-md-3 col-xs-3 col-centered" style="margin-right: 20px;">
                                <input type="text" placeholder="City" class="" name="radiusCity">
                            </div>
                            <div class="box-shadow col-lg-2 col-md-2 col-sm-2 col-xs-2 col-centered">
                                <select data-placeholder="State" class="body-bold-1-1" id="statesList" name="states" style="float:left !important;">
                                    <option></option>
                                    <c:forEach items="${stateList}" var="state">
                                        <option value="${state.abbreviation}">${state.abbreviation}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-lg-1 col-md-2 col-sm-2 col-centered">OR</div>
                            <div class="box-shadow col-lg-1 col-sm-8 col-centered">
                                <input type="text" placeholder="ZIP" class="input-clean" name="zip">
                            </div>
                        </div>
                    </div>

                    <!-- Search button row, desktop view only -->
                    <div class="hidden-xs hidden-sm">
                        <div class="row landing-search-button-container">
                            <div class="col-xs-12">
                                <div class="col-md-2 pull-right text-center">
                                    <button type="submit" class="btn landing-search-button body-bold-1-2">SEARCH</button>
                                </div>
                                <div class="col-md-2 pull-right ">
                                    <button type="button" class="btn landing-reset-button text-center body-bold-1-1" onClick="window.location.href=window.location.href">RESET</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Search button row, mobile view only -->
                    <div class="visible-xs visible-sm landing-search-button-container-mobile">
                        <div class="row row-centered" style="margin-top:15px;">
                            <div class="col-xs-3 col-centered">
                                <button type="button" class="btn landing-reset-button text-center body-bold-1-1" onClick="window.location.reload();">Reset</button>
                            </div>
                            <div class="col-xs-3 col-centered">
                                <button type="submit" class="btn landing-search-button body-bold-1-2">Search</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <c:import url="/footer.html" />

            <script>
                function refresh() {
                    window.parent.location = window.parent.location.href;
                }
            </script>

            <script>
                window.onload = function () {
                    $("#fieldsList").select2();
                    $("#schoolsList").select2();
                    $("#certificationsList").select2();
                    $("#statesList").select2();
                };
            </script>

            <script>
                $(document).ready(function () {
                    $('#statesList').select2({
                        placeholder: "State",
                        allowClear: true
                    });
                });
            </script>
        </body>

        </html>