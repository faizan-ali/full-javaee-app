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
            <title>${elt.firstName} ${elt.lastName}</title>

            <!-- Datatables Stylesheet -->
            <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.css" />

            <c:import url="school-header.jsp" />

            <!-- Page-specific CSS -->


            <!-- Datatables JS -->
            <script type="text/javascript" src="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.js"></script>

            <div class="container-fluid platform-nav-container">
                <div class="row platform-nav">

                    <!-- Desktop nav, hidden from mobile -->
                    <div class="hidden-xs hidden-sm">
                        <div class="container-fluid platform-nav-container">
                            <div class="row platform-nav">

                                <!-- Desktop nav, hidden from mobile -->
                                <div class="hidden-xs">
                                    <div class="col-md-3 platform-nav-back">
                                        <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:#181453;"></div>
                                        <div class="subhead-1-3 col-md-9">
                                            <a href="school-landing.jsp">Back</a>
                                        </div>
                                    </div>
                                    <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right ">Student List</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Mobile Nav -->
                    <div class="visible-xs visible-sm">
                        <div class="col-xs-12 text-center" style="margin-top: 8px;">
                            <div class="col-xs-6 platform-nav-button subhead-1-2 platform-nav-button-border">Student List</div>
                            <div class="col-xs-6 platform-nav-button subhead-1-2">
                                <a href="school-landing.jsp">Back</a></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="container">
                <div class="row row-centered" style="margin-top: 50px; margin-bottom: 60px;">
                    <div class="col-md-12 col-centered">
                        <table class="table table-hover" id="candidates">
                            <thead>
                                <tr class="body-bold-1-1">
                                    <th>NAME</th>
                                    <th>DATE CREATED</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="elt" items="${user.school.candidates}">
                                    <tr class="body-bold-1-1">
                                        <td><a href="../SchoolViewCandidateProfileServlet?candidateID=${elt.candidateID}">${elt.firstName} ${elt.lastName}</a> </td>
                                        <td>${elt.dateCreated}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <c:import url="school-footer.jsp" />

            <!-- Datatables Initializer -->
            <script>
                $(document).ready(function () {
                    $('#candidates').DataTable({
                        "pageLength": 25,
                        "order": [[1, "des"]]
                    });
                });
            </script>

            </body>

        </html>