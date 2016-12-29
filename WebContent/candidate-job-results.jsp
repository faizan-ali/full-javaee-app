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

                <!-- Fix for dropdown menus not working on mobile -->
                <script>
                    $('body').on('touchstart.dropdown', '.dropdown-menu', function (e) {
                        e.stopPropagation();
                    });
                </script>

                <!-- Page specific JS -->
                <script src="js/job-board-scripts.js"></script>

                <!-- Page specific CSS -->
                <link href="css/candidate-job-results.css" rel="stylesheet">

                <!-- In Platform Desktop Candidate Navbar -->
                <div class="hidden-xs container-fluid candidate-nav-desktop-container">
                    <div class="row candidate-nav-desktop">
                        <div class="col-md-3 platform-nav-back" style="margin-top:0px;">
                            <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:#181453;"></div>
                            <div class="subhead-1-3 col-md-9">
                                <a href="candidate-landing.jsp" style="color: black;">Back To Profile</a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- In Platform Mobile Candidate Navbar -->
                <div class="visible-xs container candidate-nav-mobile-container navbar-fixed-top">
                    <div class="row">
                        <div class="col-xs-10 pull-left" style="margin-top: 13px; margin-bottom: 15px;">
                            <div class="col-xs-1 icon-arrow-left-circle" style="font-size: 20px; color:#181453;"></div>
                            <div class="col-xs-10 subhead-1-3">
                                <a href="candidate-landing.jsp" style="color: black;">Back To  Profile</a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="background-container">
                    <div class="container-fluid" id="result-page-container">
                        <div class="row">

                            <!-- Filters (Desktop) -->
                            <div class="col-md-2 hidden-xs" id="filters-container">
                                <div class="filter-container" id="filter-list-container">
                                    <span class="body-bold-1-1 filter-title">Filters applied: </span>
                                    <div class="filter body-1-2">
                                        <c:if test="${not empty filterMap['location']}">
                                            <a href="JobSearchServlet?keywords=${keywords}&jobType=${jobType}&sort=${sort}&salary=${salary}" class="btn-link">Location: ${filterMap['location']} <span class="icon-close"></span></a>
                                        </c:if>

                                        <c:if test="${not empty filterMap['salary']}">
                                            <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}" class="btn-link">Salary: ${filterMap['salary']} <span class="icon-close"></span></a>
                                        </c:if>

                                        <c:if test="${not empty filterMap['jobType']}">
                                            <a href="JobSearchServlet?keywords=${keywords}&location=${location}&sort=${sort}&salary=${salary}" class="btn-link">Job Type: ${filterMap['jobType']} <span class="icon-close"></span></a>
                                        </c:if>

                                        <c:if test="${not empty filterMap['sort']}">
                                            <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&salary=${salary}" class="btn-link">Sorting By: ${filterMap['sort']} <span class="icon-close"></span></a>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="filter-container" id="sort-filter-container">
                                    <span class="body-bold-1-1 filter-title">Sort by: </span>
                                    <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=relevance&salary=${salary}" class="btn-link">relevance</a><span> | </span>
                                    <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=date&salary=${salary}" class="btn-link">date</a>
                                </div>

                                <div class="filter-container" id="salary-filter-container">
                                    <div class="body-bold-1-1 filter-title">Salary: </div>
                                    <div class="filter body-1-2">
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}&salary=$25,000" class="btn-link">$25,000+</a>
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}&salary=$30,000" class="btn-link">$30,000+</a>
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}&salary=$35,000" class="btn-link">$35,000+</a>
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}&salary=$45,000" class="btn-link">$45,000+</a>
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=${jobType}&sort=${sort}&salary=$55,000" class="btn-link">$55,000+</a>
                                    </div>
                                </div>

                                <div class="body-1-2 filter-container">
                                    <div class="body-bold-1-1 filter-title">Job Type: </div>
                                    <div class="filter body-1-2">
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=fulltime&sort=${sort}&salary=${salary}" class="btn-link">Full-time</a>
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=contract&sort=${sort}&salary=${salary}" class="btn-link">Contract</a>
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=internship&sort=${sort}&salary=${salary}" class="btn-link">Internship</a>
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=temporary&sort=${sort}&salary=${salary}" class="btn-link">Temporary</a>
                                        <a href="JobSearchServlet?keywords=${keywords}&location=${location}&jobType=parttime&sort=${sort}&salary=${salary}" class="btn-link">Part-time</a>
                                    </div>
                                </div>
                                <div class="filter-container">
                                    <div class="filter-title body-bold-1-1">Location: </div>
                                    <c:forEach var="elt" items="${locationList}">
                                        <div class="filter body-1-2">
                                            <a href="JobSearchServlet?keywords=${keywords}&location=${elt}&jobType=${jobType}&sort=${sort}&salary=${salary}" class="btn-link">${elt}</a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <!-- Job results, search boxes and mobile filters container -->
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

                                <!-- Filters (mobile) -->
                                <div class="col-xs-12 visible-xs" style="margin-top: -15px;">
                                    <div class="col-xs-12">
                                        <span class="body-bold-1-1 filter-title">Sort by: </span>
                                        <button type="submit" class="btn btn-link" onclick="submitSearchFormSort('relevance');">relevance</button> <span class="body-bold-1-"> | </span>
                                        <button type="submit" class="btn btn-link" onclick="submitSearchFormSort('date');">date</button>
                                    </div>
                                    <div class="col-xs-5 col-xs-offset-1" style="z-index: 9999 !important;">
                                        <div class="dropdown" style="margin-bottom:20px;">
                                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                                Location
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                                <c:forEach var="elt" items="${locationList}">
                                                    <li>
                                                        <button type="submit" onclick="submitSearchFormLocation('${elt}');" class="btn btn-link">${elt}</button>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-xs-5 col-xs-offset-1" style="z-index: 9999 !important;">
                                        <div class="dropdown" style="margin-bottom:20px;">
                                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                                Job Type
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                                <li>
                                                    <button type="submit" onclick="submitSearchFormJob('fulltime');" class="btn btn-link">Full-time</button>
                                                </li>
                                                <li>
                                                    <button type="submit" onclick="submitSearchFormJob('contract');" class="btn btn-link">Contract</button>
                                                </li>
                                                <li>
                                                    <button type="submit" onclick="submitSearchFormJob('internship');" class="btn btn-link">Internship</button>
                                                </li>
                                                <li>
                                                    <button type="submit" onclick="submitSearchFormJob('temporary');" class="btn btn-link">Temporary</button>
                                                </li>
                                                <li>
                                                    <button type="submit" onclick="submitSearchFormJob('parttime');" class="btn btn-link">Part-time</button>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                </div>

                                <!-- Job results -->
                                <div class="col-md-7 scroll">
                                    <div class="pull-right body-1-1">Jobs ${searchResponse.start} to ${searchResponse.end} of ${searchResponse.totalResults}</div>
                                    <c:forEach var="elt" items="${searchResponse.results}">
                                        <div class="result-container">
                                            <div class="body-bold-1-1 job-title"><a href="${elt.URL}" target="_blank">${elt.jobTitle}</a></div>
                                            <div class="company-location">
                                                <span class="company-title body-bold-1-1">${elt.company}</span>
                                                <span class="location text-muted body-1-1"> - ${elt.location}</span>
                                            </div>
                                            <div class="snippet body-1-1">${elt.snippet}</div>
                                            <div class="date-posted body-1-1 text-muted">Posted ${elt.date}</div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Message box -->
                                <div class="col-md-2 col-md-offset-7 hidden-xs" id="message-box-container">
                                    <div class="col-md-12 box-shadow hidden-xs" id="message-box">
                                        <div class="text-center" id="message">
                                            <div class="body-bold-1-1" style="font-size: 18px;">Keep your profile up to date for a curated list of recommended jobs!</div>
                                        </div>

                                        <a href="candidate-landing.jsp">
                                            <button class="hidden-xs btn btn-link edit-profile-button body-bold-1-2">EDIT PROFILE</button>
                                        </a>
                                    </div>
                                </div>

                                <!-- Pagination -->
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


                </body>

            </html>