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
                <title>Search Results</title>

                <c:import url="/header.jsp" />

                <!-- Page specific JS -->
                <script src="js/job-board-scripts.js"></script>

                <!-- Page specific CSS -->
                <link href="css/job-results.css" rel="stylesheet">


                <div class="background-container">
                    <div class="container-fluid" id="result-page-container">
                        <div class="row">
                            <!-- Filters -->
                            <div class="col-md-2" id="filters-container">
                                <div class="body-1-2" id="sort-filter-container">
                                    <span class="body-bold-1-1 filter-title">Sort by: </span>
                                    <button type="submit" class="btn btn-link body-1-2" onclick="submitSearchFormSort('relevance');">relevance</button> <span class="body-bold-1-"> | </span>
                                    <button type="submit" class="btn btn-link body-1-2" onclick="submitSearchFormSort('date');">date</button>
                                </div>
                                <div class="body-1-2">
                                    <div class="body-bold-1-1 filter-title">Job Type</div>
                                    <div class="filter body-1-2">
                                        <button type="submit" onclick="submitSearchFormJob('fulltime');" class="btn btn-link">Full-time</button>
                                        <button type="submit" onclick="submitSearchFormJob('contract');" class="btn btn-link">Contract</button>
                                        <button type="submit" onclick="submitSearchFormJob('internship');" class="btn btn-link">Internship</button>
                                        <button type="submit" onclick="submitSearchFormJob('temporary');" class="btn btn-link">Temporary</button>
                                        <button type="submit" onclick="submitSearchFormJob('parttime');" class="btn btn-link">Part-time</button>
                                    </div>
                                </div>
                                <div class="">
                                    <div class="filter-title body-bold-1-1">Location</div>
                                    <c:forEach var="elt" items="${locationList}">
                                        <div class="filter body-1-2">
                                            <button type="submit" onclick="submitSearchFormLocation('${elt}');" class="btn btn-link">${elt}</button>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <!-- Job results and search boxes container -->
                            <div class="col-md-10 col-md-offset-2">

                                <!-- Search boxes -->
                                <div class="col-md-12 remove-horizontal-padding" id="job-search-container">
                                    <!-- Form for search -->
                                    <form action="JobSearchServlet" id="search-form">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <input type="text" class="form-control" placeholder="Job Title, Keywords or Company" name="keywords" value="${keywords}" />
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <input type="text" class="form-control" placeholder="City, State or Zip Code" name="location" value="${location}" />
                                            </div>
                                        </div>
                                        <input type="hidden" name="jobType" value="">
                                        <input type="hidden" name="sort" value="">
                                        <div class="col-md-2">
                                            <button type="submit" class="btn job-search-button body-bold-1-2">SEARCH</button>
                                        </div>
                                    </form>
                                </div>

                                <!-- Job results -->
                                <div class="col-md-7 scroll">
                                    <div class="pull-right body-1-1">Jobs ${searchResponse.start} to ${searchResponse.end} of ${searchResponse.totalResults}</div>
                                    <c:forEach var="elt" items="${searchResponse.results}">
                                        <div class="result-container">
                                            <div class="body-bold-1-1 job-title"><a href="${elt.URL}" target="_blank">${elt.jobTitle}</a></div>
                                            <div class="company-location">
                                                <span class="company-title body-bold-1-1">${elt.company}</span>
                                                <span class="location text-muted body-1-1"> - ${elt.formattedLocationFull}</span>
                                            </div>
                                            <div class="snippet body-1-1">${elt.snippet}</div>
                                            <div class="date-posted body-1-1 text-muted">Posted ${elt.formattedRelativeTime}</div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <div class="col-md-7" id="pagination">
                                    <div class="text-center">
                                        <ul class="pagination">
                                            <c:choose>
                                                <c:when test="${currentPage gt 1}">
                                                    <li>
                                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}&start=${(currentPage - 2) * 25}" aria-label="Previous">
                                                            <span>&laquo;</span>
                                                        </a>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="disabled">
                                                        <a href="" aria-label="Previous">
                                                            <span>&laquo;</span>
                                                        </a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>

                                            <c:forEach var="page" begin="1" end="${paginationEnd}">
                                                <c:choose>
                                                    <c:when test="${page eq currentPage}">
                                                        <li class="active"><a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}&start=${(page - 1 )* 25}">${page}</a>
                                                        </li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li><a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}&start=${(page - 1 )* 25}">${page}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>


                                            <c:choose>
                                                <c:when test="${currentPage lt paginationEnd}">
                                                    <li>
                                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}&start=${currentPage * 25}" aria-label="Next">
                                                            <span>&raquo;</span>
                                                        </a>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="disabled">
                                                        <a href="" aria-label="Next">
                                                            <span>&raquo;</span>
                                                        </a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

                <c:import url="/footer.html" />

                </body>

            </html>