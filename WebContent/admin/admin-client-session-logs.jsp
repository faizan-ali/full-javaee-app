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
                <title></title>

                <!-- Datatables Stylesheet -->
                <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.css" />


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
                                        <a href="admin-client-logs.jsp">Back To Session List</a>
                                    </div>
                                </div>
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right">Session Logs</div>
                            </div>
                        </div>
                    </div>

                    <div class="container-fluid">
                        <div class="row row-centered">
                            <h2 class="archer text-center">Session statistics for ${client.firstName} ${client.lastName}: ${sessionLength} minutes</h2>

                            <div class="col-md-2 col-centered" style="margin-bottom: 85px;">
                                <h3 class="text-center archer">Candidates Viewed <span class = "badge">${profilesViewed.size()}</span></h3>
                                <table class="table table-hover" id="candidates-table">
                                    <thead>
                                        <tr class="body-bold-1-1">
                                            <th>NAME</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="profile" items="${profilesViewed}">
                                            <tr class="body-bold-1-1">
                                                <td>
                                                    <form action="../AdministratorViewCandidateProfileServlet">
                                                        <input type="hidden" name="candidateID" value="${profile.candidateID}">
                                                        <button class="btn btn-link" type="submit">${profile.firstName} ${profile.lastName}</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="col-md-4 col-md-offset-1 col-centered" style="margin-bottom: 85px;">
                                <h3 class="text-center archer">Searches</h3>
                                <table class="table table-hover" id="query-table">
                                    <thead>
                                        <tr class="body-bold-1-1">
                                            <th>Search Criteria (State;School;Field;Certs)</th>
                                            <th>Search Keyword</th>
                                            <th>Radius (Distance;City/ZIP)</th>
                                            <th>Time</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="loginLog" items="${client.loginLog}">
                                            <c:forEach var="searchLog" items="${loginLog.searchLog}">
                                                <tr class="body-bold-1-1">
                                                    <td>${searchLog.searchCriteria}</td>
                                                    <td>${searchLog.searchKeyword}</td>
                                                    <td>${searchLog.radius}</td>
                                                    <td>${searchLog.time}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="col-md-3 col-md-offset-1 col-centered" style="margin-bottom: 85px;">
                                <h3 class="text-center archer">Pipeline Changes <span class = "badge">${profilesChanged.size()}</span></h3>

                                <table class="table table-hover" id="pipeline-table">
                                    <thead>
                                        <tr class="body-bold-1-1">
                                            <th>CANDIDATES</th>
                                            <th>CHANGES</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="entry" items="${profilesChanged}">
                                            <tr class="body-bold-1-1">
                                                <td>
                                                    <form action="../ViewFullCandidateProfileServlet">
                                                        <input type="hidden" name="candidateID" value="${entry.key.candidateID}">
                                                        <button class="btn btn-link" type="submit">${entry.key.firstName} ${entry.key.lastName}</button>
                                                    </form>
                                                </td>
                                                <td>${entry.value}</td>
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
                            $('#query-table').DataTable({
                                "pageLength": 100,
                                stateSave: true,
                                "order": [[0, "des"]]
                            });
                        </script>
                        </body>

            </html>