<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
            <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
            <html>

            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <meta http-equiv="refresh" content="10;url=results.jsp" />
                <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags.
Scrolling is disabled for this page. -->
                <title>Restrictions</title>
                <c:import url="/header.jsp" />

                <div class="container-fluid platform-nav-container">
                    <div class="row platform-nav">

                        <!-- Desktop nav, hidden from mobile -->
                        <div class="hidden-xs">
                            <div class="col-md-4 platform-nav-back">
                                <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:#181453;"></div>
                                <div class="subhead-1-3 col-md-9">
                                    <a href="results.jsp">Back To Candidate Search</a>
                                </div>
                            </div>
                            <div class="col-md-4 platform-nav-button pull-right" id="navbar-view-message">
                                <div class="col-md-1 icon-settings" style="font-size: 20px; color: #3B3D43"></div>
                                <div class="col-md-11 subhead-1-1">Your Account</div>
                            </div>
                        </div>


                        <!-- Mobile Nav -->
                        <div class="visible-xs">
                            <div class="col-xs-12 platform-nav-back text-center">
                                <div class="icon-arrow-left-circle col-xs-1" style="font-size: 20px; color:#181453;"></div>
                                <div class="subhead-1-3 col-xs-9">
                                    <a href="landing.jsp">Back To Candidate Search</a>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

                <div class="background-container">
                    <div class="container" style="height: 380px;">
                        <div class="row row-centered" style="margin-top: 150px; margin-bottom: 10px;">
                            <div class="col-md-8 col-centered">
                                <h2 class="heading-1-1 text-center">Candidate outside geographical restrictions</h2>
                                <div class="body-1-1 text-center" style="font-size: 18px !important; margin-top: 20px;">You have attempted to view a candidate outside your geographical limit. If you believe this is an error or for more information please contact us at info@workamerica.co</div>
                                <div class="body-1-1 text-center" style="font-size: 18px !important; margin-top: 20px;">You will now be automatically redirected to your search results</div>
                            </div>
                        </div>
                    </div>
                </div>
                <c:import url="/footer.html" />
                </body>

            </html>