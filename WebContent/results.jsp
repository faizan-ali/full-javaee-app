<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
            <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
            <html>

            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags.
            Scrolling is disabled for this page. -->
                <title>Search Results</title>

                <!-- Datatables Stylesheet -->
                <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.css" />

                <c:import url="/header.jsp" />

                <!-- Page specific CSS -->
                <link href="css/results.css" rel="stylesheet">


                <!-- Datatables JS -->
                <script type="text/javascript" src="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.js"></script>


                <div class="container-fluid platform-nav-container">
                    <div class="row platform-nav">

                        <!-- Desktop nav, hidden from mobile -->
                        <div class="hidden-xs hidden-sm">
                            <div class="col-lg-5 col-md-6 col-sm-6  platform-nav-back">
                                <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:white"></div>
                                <div class="subhead-1-3 col-md-9" style = "color:white !important;">
                                    <a href="landing.jsp">Back To Candidate Search</a>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-3 text-center platform-nav-button subhead-1-2 pull-right" style = "color:white !important;"><a href="pipeline.jsp">Candidate Pipeline</a></div>
                            <div class="col-lg-2 col-md-3 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right" style = "color:white !important;"><a href="landing.jsp">Search Candidates</a></div>
                        </div>

                        <!-- Mobile Nav -->
                        <div class="visible-xs visible-sm">
                            <div class="col-xs-12 text-center" style="margin-top: 8px;">

                                <div class="icon-arrow-left-circle col-xs-1" style="font-size: 16px; color:white;"></div>
                                <div class="subhead-1-3 col-xs-10 remove-horizontal-padding-mobile" style="font-size: 12px !important;">
                                    <a href="landing.jsp" style = "color:white !important">Back To Candidate Search</a>
                                </div>

                                <div class="col-xs-6 platform-nav-button subhead-1-2 platform-nav-button-border" style = "color:white !important;">Search Candidates</div>
                                <div class="col-xs-6 platform-nav-button subhead-1-2" style = "color:white !important;">Candidate Pipeline</div>
                            </div>

                        </div>
                    </div>
                </div>

                <!-- Container for search results -->
                <div class="container-fluid" id="results-background-container">
                    <div class="row" id="results-title-controls-container">
                        <div class="col-md-6" id="results-title-container">
                            <div class="heading-1-1" id="results-title">Search Results</div>
                            <div class="results-subtitle body-1-1"></div>
                        </div>
                        <div class="col-md-6 results-controls"></div>
                    </div>

                    <div class="row" id="results-container">

                        <div style="overflow-x: auto">
                            <table id="result-table" class="table table-hover box-shadow">
                                <thead>
                                    <tr class="body-bold-1-4">
                                        <th>STATUS</th>
                                        <th>NAME</th>
                                        <th>LOCATION</th>
                                        <th>SCHOOL</th>
                                        <th>FIELD OF STUDY</th>
                                        <th>COMPLETION DATE</th>
                                        <th>CERTIFICATIONS
                                            <div class="body-1-3">Obtained/Anticipated</div>
                                        </th>
                                        <th>Date Created</th>
                                        <th>FirstName</th>
                                        <th>LastNAme</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="elt" items="${listCandidates}">
                                        <c:choose>
                                            <c:when test="${fn:contains(viewList, elt.candidateID)}">
                                                <tr class="body-bold-1-1">
                                            </c:when>
                                            <c:otherwise>
                                                <tr class="body-bold-1-1 table-row-striped">
                                            </c:otherwise>
                                        </c:choose>
                                        <td style="font-size: 11px !important;">
                                            <c:choose>
                                                <c:when test="${elt.pipelineStatus eq 'Hired'}">
                                                    <div class="hired"></div>
                                                </c:when>
                                                <c:when test="${elt.pipelineStatus eq 'Offer Extended'}">
                                                    <div class="offer"></div>
                                                </c:when>
                                                <c:when test="${elt.pipelineStatus eq 'Scheduled for Interview'} ">
                                                    <div class="scheduled"></div>
                                                </c:when>
                                                <c:when test="${elt.pipelineStatus eq 'Under Review'}">
                                                    <div class="review"></div>
                                                </c:when>
                                                <c:when test="${elt.pipelineStatus eq 'Declined'}">
                                                    <div class="declined"></div>
                                                </c:when>
                                                <c:when test="${elt.pipelineStatus eq 'Not Interested'}">
                                                    <div class="declined"></div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="unread"></div>
                                                </c:otherwise>
                                            </c:choose>
                                            <span>${elt.pipelineStatus}</span>
                                        </td>
                                        <td>
                                            <form action="ViewFullCandidateProfileServlet">
                                                <a href="#">
                                                    <input type="hidden" name="pipelineJSP" value="false">
                                                    <input type="hidden" name="resultsJSP" value="true">
                                                    <input type="hidden" name="candidateID" value="${elt.candidateID}">
                                                    <input type="submit" name="View Profile" value="${elt.firstName} ${elt.lastName}" style="vertical-align: bottom; overflow: visible; display: inline; margin: 0; padding: 0; border: 0; cursor: pointer; background: none;">
                                                </a>
                                            </form>
                                        </td>
                                        <td>
                                            <c:if test="${not empty elt.city}">
                                                ${elt.city},
                                            </c:if>
                                            ${elt.state}</td>
                                        <td>${elt.school}</td>
                                        <td>${elt.field}</td>
                                        <td>${elt.completionDate}</td>


                                        <td>
                                            <c:if test="${not empty elt.obtainedCertification}">
                                                <div class="row">
                                                    <span class="detail-1-2">O </span><span class="body-bold-1-5">- ${elt.obtainedCertification}</span>
                                                </div>
                                            </c:if>
                                            <c:if test="${not empty elt.anticipatedCertification}">
                                                <div class="row">
                                                    <span class="detail-1-2">A </span><span class="body-bold-1-5">- ${elt.anticipatedCertification}</span>
                                                </div>
                                            </c:if>
                                        </td>
                                        <td>${elt.dateCreated}</td>
                                        <td>${elt.firstName}</td>
                                        <td>${elt.lastName}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <c:import url="/footer.html" />

                <!-- Datatables Initializer -->
                <script>
                    $('#result-table').DataTable({
                        "pageLength": 25,
                        stateSave: true,
                        "columnDefs": [
                            {
                                "targets": [7],
                                "visible": false,
                                "searchable": false
                             }





















                            , {
                                "targets": [8],
                                "visible": false
                            }





















                            , {
                                "targets": [9],
                                "visible": false
                            }
                        ],
                        "order": [[7, "des"]]
                    });
                </script>
                </body>

            </html>