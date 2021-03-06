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
                <title>${schoolAccount.school.name} Logs</title>

                <!-- Datatables Stylesheet -->
                <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.css" />

                <!-- Page specific CSS -->
                <link href="css/admin.css" rel="stylesheet">

                <%@ include file = "/admin/admin-header.jsp"%>

                    <!-- Datatables JS -->
                    <script type="text/javascript" src="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.js"></script>

                    <!-- In platform navbar -->
                    <div class="container-fluid platform-nav-container">
                        <div class="row platform-nav">

                            <!-- Desktop nav, hidden from mobile -->
                            <div class="hidden-xs">
                                <div class="col-md-3 platform-nav-back">
                                    <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:#181453;"></div>
                                    <div class="subhead-1-3 col-md-9">
                                        <a href="admin-school-logs-list.jsp">Back To School List</a>
                                    </div>
                                </div>
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right">${schoolAccount.school.name} Logs</div>
                            </div>
                        </div>
                    </div>

                    <div class="container-fluid">
                        <div class="row row-centered" style="margin-top: 10px; margin-bottom: 100px;">
                            <div class="col-md-3 col-centered">
                                <h3 class="archer text-center">Logs</h3>
                                <table class="table table-hover" id="log-table">
                                    <thead>
                                        <tr class="body-bold-1-1">
                                            <th>DATE</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="log" items="${schoolAccount.loginLog}">
                                            <tr class="body-bold-1-1">
                                                <td>
                                                    <form action="../AdministratorSchoolSessionStatisticsServlet">
                                                        <input type="hidden" name="logID" value="${log.schoolLoginLogID}" />
                                                        <button type="submit" class="btn btn-link">${log.date} | ${log.time}</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="col-md-7 col-md-offset-1 col-centered" style="margin-bottom: 70px;">
                                <h3 class="archer text-center">Actions</h3>
                                <table class="table table-hover" id="session-table">
                                    <thead>
                                        <tr class="body-bold-1-1">
                                            <th>TIME</th>
                                            <th>ACTION</th>
                                            <th>CANDIDATE</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="elt" items="${schoolActivityLogs}">
                                            <tr class="body-bold-1-1">
                                                <td>${elt.time}</td>
                                                <td>${elt.schoolActivity}</td>
                                                <td>
                                                    <form action="../AdministratorViewCandidateProfileServlet">
                                                        <input type="hidden" name="candidateID" value="${elt.candidateID}">
                                                        <button class="btn btn-link" type="submit">${elt.candidate.firstName} ${elt.candidate.lastName}</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>



                    <%@ include file = "/admin/admin-footer.jsp" %>

                        <!-- Datatables Initializer -->
                        <script>
                            $('#log-table').DataTable({
                                "pageLength": 15,
                                stateSave: true,
                                "order": [[0, "des"]]
                            });
                        </script>

                        <script>
                            $('#session-table').DataTable({
                                "pageLength": 15,
                                stateSave: true,
                                "order": [[0, "des"]]
                            });
                        </script>

                        </body>

            </html>