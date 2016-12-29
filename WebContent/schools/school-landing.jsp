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
            <title>WorkAmerica Partner Platform</title>

            <c:import url="school-header.jsp" />

            <!-- Page-specific CSS -->
            <link href="../css/landing.css" rel="stylesheet">


            <div class="container-fluid platform-nav-container">
                <div class="row platform-nav">

                    <!-- Desktop nav, hidden from mobile -->
                    <div class="hidden-xs hidden-sm">
                        <div class="col-lg-5 col-md-6 col-sm-6 platform-nav-back">
                            <div class="icon-settings col-md-1" style="font-size: 20px; color:#181453;"></div>
                            <div class="subhead-1-1 col-md-5">
                                <a href="school-account.jsp" style="color: black;">Account Settings</a>
                            </div>
                            <div class="icon-logout col-md-1 col-md-offset-1" style="font-size: 20px; color:#181453;"></div>
                            <div class="col-md-4">
                                <form action="LogoutServlet">
                                    <button type="submit" class="btn btn-link subhead-1-1 remove-horizontal-padding remove-vertical-padding">Logout</button>
                                </form>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-3 text-center platform-nav-button subhead-1-2 pull-right"><a href="">${user.school.name}</a></div>
                        <div class="col-lg-2 col-md-3 col-sm-3 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right">Landing Page</div>
                    </div>

                    <!-- Mobile Nav -->
                    <div class="visible-xs visible-sm">
                        <div class="col-xs-12 text-center" style="margin-top: 8px;">
                            <div class="icon-settings col-xs-1" style="font-size: 14px; color:#181453;"></div>
                            <div class="subhead-1-1 col-xs-2">
                                <a href="school-account.jsp" style="color: black; font-size: 11px;">Settings</a>
                            </div>
                            <div class="icon-logout col-xs-1 col-xs-offset-4" style="font-size: 14px; color:#181453;"></div>
                            <div class="col-xs-2" style="font-size: 11px !important;">
                                <form action="LogoutServlet">
                                    <button type="submit" class="btn btn-link subhead-1-1 remove-horizontal-padding remove-vertical-padding" style="font-size: 11px !important;">Logout</button>
                                </form>
                            </div>
                            <div class="col-xs-6 platform-nav-button subhead-1-2 platform-nav-button-border">Landing Page</div>
                            <div class="col-xs-6 platform-nav-button subhead-1-2">
                                <a href="">${user.school.name}</a></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="container">
                <div class="row row-centered">
                    <div class="col-md-4 col-centered">
                        <a href="school-candidates.jsp" class="btn btn-warning">View Students in Platform</a>
                        <a href="school-new-profile.jsp" class="btn btn-warning">Add Student</a>
                    </div>
                </div>
            </div>

            <c:import url="school-footer.jsp" />

            </body>

        </html>