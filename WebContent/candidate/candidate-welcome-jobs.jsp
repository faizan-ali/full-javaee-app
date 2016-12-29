<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

            <div class="col-md-4 col-xs-12" id="jobs-box">

                <!-- Welcome box -->
                <div class="col-md-12 box-shadow" id="welcome-container">
                    <div class="hidden-xs">
                        <div class="heading-1-1">Welcome to your </div>
                        <div class="heading-1-1">WorkAmerica Profile!</div>
                    </div>

                    <div class="visible-xs text-center">
                        <h4 style="font-family: 'Archer-pro-medium'; color: #3B3D43;">Welcome to your WorkAmerica Profile!</h4>
                    </div>

                    <div class="welcome-body-container body-1-1">
                        <div class="welcome-body-box">Local employers use WorkAmerica to find candidates like you in the Skilled Trades, Allied Health, or IT. </div>

                        <div class="welcome-body-box">Complete your simple profile and employers can start reaching out to you right away.</div>

                        <div class="welcome-body-box visible-xs visible-sm">
                            Please click on <em>Edit</em> below to start filling out your profile!
                        </div>
                    </div>
                    <div class="candidate-help-container">
                        <div class="row">
                            <div class="col-xs-1 icon-question" style="font-size: 20px; color: #E32235;"></div>
                            <div class="col-xs-8 body-bold-1-3">NEED HELP?</div>
                        </div>
                        <div class="row">
                            <div class="col-xs-3 body-bold-1-5">Call</div>
                            <div class="col-xs-8 body-1-1">877-750-2968 Ext. 2</div>
                        </div>
                        <div class="row">
                            <div class="col-xs-3 body-bold-1-5">Email</div>
                            <div class="col-xs-8 body-1-1">jobs@workamerica.co</div>
                        </div>
                    </div>

                    <button class="hidden-xs hidden-sm btn btn-link edit-profile-button body-bold-1-2" onClick="toggleEditContainer();" id="edit-button">EDIT PROFILE
                    </button>
                </div>

                <!-- Job box -->
                <div class="col-md-12 box-shadow hidden-xs" id="jobs-container">
                    <div class="row row-centered hidden" id="job-search-container">
                        <div class="heading-1-1 text-center">Job Search</div>
                        <form action="JobSearchServlet">
                            <div class="col-md-6 col-centered">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="keywords" placeholder="Job Title or Keywords">
                                </div>
                            </div>
                            <div class="col-md-6 col-centered">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="location" placeholder="City, State or Zipcode">
                                </div>
                            </div>
                            <div class="col-md-4 col-centered">
                                <button type="submit" class="btn job-search-button body-bold-1-2">SEARCH</button>
                            </div>
                        </form>
                    </div>
                    <div class="heading-1-1 text-center">
                        Recommended Jobs
                    </div>
                    <div id="job-list-container" class="body-1-1">
                        <c:choose>
                            <c:when test="${not empty jobResponse}">
                                <i class="icon-arrow-up" id="job-list-prev"></i>
                                <ul id="job-list">
                                    <c:forEach var="elt" items="${jobResponse.results}">
                                        <c:if test="${not empty elt.snippet}">
                                            <li class="body-1-1">
                                                <div class="detail-1-2">${elt.jobTitle}</div>
                                                <span>${elt.snippet}</span>
                                                <span><a href="${elt.URL}" target="_blank">Read more</a></span>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                                <div>
                                    <i class="icon-arrow-down" id="job-list-next"></i>
                                </div>
                                <div class="pull-right hidden" style="margin-bottom: 12px;"><a href="JobSearchServlet?keywords=${user.field} or (${user.anticipatedCertification})&location=${user.city}, ${user.state}">See more</a></div>
                            </c:when>
                            <c:otherwise>
                                <div class="body-1-1 text-center" style="margin-bottom:25px; margin-top:25px;">Please update your profile for a list of recommended jobs.</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="archer text-center">Powered By</div>
                        </div>
                    </div>
                    <div class="row row-centered">
                        <div class="col-md-4 col-centered"><img src="img/indeed.png" alt="Indeed" width="90px" class=""></div>
                        <div class="col-md-4 col-centered" style="margin-bottom: 10px;"><img src="img/careerbuilder.png" alt="CareerBuilder" width="90px" class=""></div>
                    </div>
                </div>
            </div>