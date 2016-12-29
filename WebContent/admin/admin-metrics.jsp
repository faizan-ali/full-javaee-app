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
                <title>Daily Metrics</title>

                <%@ include file = "/admin/admin-header.jsp"%>

                    <div class="container-fluid platform-nav-container">
                        <div class="row platform-nav">

                            <!-- Desktop nav, hidden from mobile -->
                            <div class="hidden-xs">
                                <div class="col-md-3 platform-nav-back">
                                    <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:#181453;"></div>
                                    <div class="subhead-1-3 col-md-9">
                                        <a href="administration.jsp">Back To Main Administration</a>
                                    </div>
                                </div>
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right">Daily Growth</div>
                            </div>
                        </div>
                    </div>


                    <div class="container-fluid">
                        <div class="row row-centered" style="margin-top: 60px;">
                            <div class="col-md-3 col-centered">
                                <div class="heading-1-1" style="margin-bottom: 50px;">Total Candidates: ${fn:length(candidateList)}</div>

                                <div class="heading-1-1" style="margin-bottom: 25px;">New today: ${fn:length(newCandidateList)}</div>

                                <div class="heading-1-1">States: </div>
                                <c:forEach var="elt" items="${candidatesMap}">
                                    <div class="heading-1-1">${elt.key}: ${elt.value}</div>
                                </c:forEach>

                                <a class="btn btn-success pull-right" href="VisualizationServlet" id="refresh" style="margin-top: 150px;">Refresh</a>
                            </div>
                        </div>
                    </div>

                    <%@ include file = "/admin/admin-footer.jsp" %>
                        <script>
                            function refresh() {
                                document.getElementById('refresh').click();
                            }
                            setInterval(refresh, 30000);
                        </script>

                        </body>

            </html>