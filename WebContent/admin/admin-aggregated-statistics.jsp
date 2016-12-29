<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
            <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
            <html>

            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags.
            Scrolling is disabled for this page. -->
                <title>${currentStats.date} Statistics</title>
                <!-- Datatables Stylesheet -->
                <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.css" />

                <%@ include file = "/admin/admin-header.jsp"%>

                    <!-- Datatables JS -->
                    <script type="text/javascript" src="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.js"></script>

                    <div class="container-fluid platform-nav-container">
                        <div class="row platform-nav">

                            <!-- Desktop nav, hidden from mobile -->
                            <div class="hidden-xs">
                                <div class="col-md-3 platform-nav-back">
                                    <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:#181453;"></div>
                                    <div class="subhead-1-3 col-md-9">
                                        <a href="admin-statistics-list.jsp">Back To Statistics List</a>
                                    </div>
                                </div>
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right ">${date}</div>
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 pull-right "><a href="administration.jsp">Click here for Administration Home</a></div>
                            </div>
                        </div>
                    </div>

                    <div class="container-fluid" style="margin-bottom: 55px;">
                        <div class="row row-centered" style="margin-top: 25px;">
                            <div class="col-md-4 col-centered">
                                <ul class="list-group">
                                    <h4 class="archer text-center">Accounts</h4>
                                    <li class="list-group-item">
                                        <span class="badge">${totalProfiles}</span>Total Accounts
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">${totalProfilesLastWeek}</span> Last Week
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">${profilesUpTillLastMonth}</span> Last Month
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">${totalNewProfiles}</span> + Accounts Last Week
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">${profilesAddedLastMonth}</span> + Accounts Last Month
                                    </li>
                                </ul>
                            </div>
                            <div class="col-md-4 col-centered">
                                <ul class="list-group">
                                    <h4 class="archer text-center">Profiles</h4>
                                    <li class="list-group-item">
                                        <span class="badge">${totalCompleteProfiles}</span> Total Profiles
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">${totalCompleteProfilesLastWeek}</span> Last Week
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">${completeProfilesUpTillLastMonth}</span> Last Month
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">${totalNewCompleteProfiles}</span> + Profiles Last Week
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">${completeProfilesAddedLastMonth}</span> + Profiles Last Month
                                    </li>
                                </ul>
                            </div>
                            <div class="col-md-4 col-centered">
                                <ul class="list-group">
                                    <h4 class="archer text-center">Resumes</h4>
                                    <li class="list-group-item">
                                        <span class="badge">${resumeCheck[0]}</span> Yes
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">${resumeCheck[1]}</span> No
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="row row-centered" style="margin-top: 25px;">
                            <div class="col-md-4 col-centered">
                                <h4 class="archer text-center">Profiles by State</h4>
                                <table id="statesList" class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>State</th>
                                            <th>Total</th>
                                            <th>Last Week</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="state" items="${statesMap}">
                                            <tr>
                                                <td>${state.key}</td>
                                                <td>${state.value[0]}</td>
                                                <td>+${state.value[1]}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-md-4 col-centered">
                                <h4 class="archer text-center">Profiles by School</h4>
                                <table id="schoolsList" class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>School</th>
                                            <th>Total</th>
                                            <th>Last Week</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="school" items="${schoolsMap}">
                                            <tr>
                                                <td>${school.key}</td>
                                                <td>${school.value[0]}</td>
                                                <td>+${school.value[1]}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-md-4 col-centered">
                                <h4 class="archer text-center">Profiles by Field</h4>
                                <table id="fieldsList" class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>Field</th>
                                            <th>Total</th>
                                            <th>Last Week</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="field" items="${fieldsMap}">
                                            <tr>
                                                <td>${field.key}</td>
                                                <td>${field.value[0]}</td>
                                                <td>+${field.value[1]}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="row row-centered" style="margin-top: 25px;">
                            <div class="col-md-3 body-1-1 col-centered">
                                <h4 class="archer text-center">Account Creation</h4>
                                <div>Work America Created: ${accountMap["WorkAmerica Created"]}</div>
                                <div>User Created: ${accountMap["User Created"]}</div>
                                <div>Unknown: ${accountMap["Unknown"]}</div>
                            </div>
                            <div class="col-md-3 body-1-1 col-centered">
                                <h4 class="archer text-center">Source Type (if relevant)</h4>
                                <c:forEach var="type" items="${sourceMap}">
                                    <div>${type.key}: ${type.value}</div>
                                </c:forEach>
                            </div>
                            <div class="col-md-3 body-1-1 col-centered">
                                <h4 class="archer text-center">Marketing Campaign</h4>
                                <c:forEach var="type" items="${campaignMap}">
                                    <div>${type.key}: ${type.value}</div>
                                </c:forEach>
                            </div>
                            <div class="col-md-3 body-1-1 col-centered">
                                <h4 class="archer text-center">Device</h4>
                                <c:forEach var="device" items="${deviceMap}">
                                    <div>${device.key}: ${device.value}</div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="row row-centered" style="margin-bottom: 35px; margin-top: 25px;">
                            <h4 class="archer text-center">Browsers</h4>
                            <c:forEach var="browser" items="${browserMap}">
                                <div class="col-md-4 body-1-1 col-centered">
                                    <div class="list-group">
                                        <div class="list-group-item active">${browser.key} <span class="badge">${browser.value["Total"]}</span></div>
                                        <c:forEach var="version" items="${browser.value}">
                                            <c:if test="${version.key ne 'Total'}">
                                                <div class="list-group-item">${version.key} <span class="badge">${version.value}</span></div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="row row-centered" style="margin-bottom: 35px; margin-top: 25px;">
                            <h4 class="archer text-center">Operating Systems</h4>
                            <c:forEach var="os" items="${osMap}">
                                <div class="col-md-4 body-1-1 col-centered">
                                    <div class="list-group">
                                        <div class="list-group-item active">${os.key} <span class="badge">${os.value["Total"]}</span></div>
                                        <c:forEach var="version" items="${os.value}">
                                            <c:if test="${version.key ne 'Total'}">
                                                <div class="list-group-item">${version.key} <span class="badge">${version.value}</span></div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <%@ include file = "/admin/admin-footer.jsp" %>
                        <script>
                            $(document).ready(function () {
                                $('#schoolsList').DataTable({
                                    responsive: true,
                                    "order": [[0, "asc"]],
                                    paging: false
                                });
                                $('#fieldsList').DataTable({
                                    responsive: true,
                                    "order": [[0, "asc"]],
                                    paging: false
                                });
                                $('#statesList').DataTable({
                                    responsive: true,
                                    "order": [[0, "asc"]],
                                    paging: false
                                });
                            });
                        </script>

                        </body>

            </html>