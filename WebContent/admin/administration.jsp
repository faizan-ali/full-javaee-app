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
            <title>Administration</title>

            <%@ include file = "/admin/admin-header.jsp"%>

                <div class="container-fluid" id="administration-container">
                    <div class="row">
                        <div class="col-md-3 col-md-offset-1">
                            <a href="admin-client-list.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">1. Manage Employers</div>
                            </a>
                            <div class="body-1-1">
                                This feature provides a list and details of all employers within the platform. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Edit name</li>
                                    <li>Assign company</li>
                                    <li>Update e-mail & password</li>
                                    <li>View number of profiles viewed (all time & current month)</li>
                                    <li>Set profile view limit</li>
                                    <li>Set geographical limit</li>
                                    <li>Approve/Unapprove employers from viewing candidates</li>
                                    <li>Create employer</li>
                                    <li>Search for specific employer</li>
                                </ol>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <a href="admin-candidate-list.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">2. Manage Candidates</div>
                            </a>
                            <div class="body-1-1">
                                This feature provides a list and details of all candidates within the platform. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Delete candidate</li>
                                    <li>Resend welcome e-mail</li>
                                    <li>Unapprove candidate</li>
                                    <li>View and edit full candidate profiles</li>
                                    <li>Search for specific candidate</li>
                                </ol>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <a href="admin-criteria-list.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">3. Manage Criteria</div>
                            </a>
                            <div class="body-1-1">
                                This feature provides a list of all search and signup criteria; state, schools, fields, and certifications. Employers use this criteria to search, and candidates use it to update their profiles. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Edit criteria</li>
                                    <li>Add criteria</li>
                                    <li>Search for criteria</li>
                                    <li>Delete criteria (under construction)</li>
                                </ol>
                            </div>
                        </div>


                    </div>
                    <div class="row">
                        <div class="col-md-3 col-md-offset-1">
                            <a href="../WeeklyStatsServlet">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">4. Weekly Statistics</div>
                            </a>
                            <div class="body-1-1">
                                This feature displays a list of Mondays (since the feature was deployed). Clicking on a particular Monday will show detailed candidate profile growth for the preceeding week. The following details are displayed (per week):
                                <ol class="body-1-1">
                                    <li>Total profiles (complete and incomplete)</li>
                                    <li>Profile growth over the week</li>
                                    <li>Profile growth over previous month (4 weeks ago from chosen date)</li>
                                    <li>Profile growth by state</li>
                                    <li>Profile growth by school</li>
                                    <li>Profile growth by field</li>
                                    <li>Search through/narrow down growth criteria</li>
                                    <li>Total resumes in platform</li>
                                    <li>Number of accounts created by users and WorkAmerica</li>
                                    <li>Source of profiles</li>
                                    <li>Devices used to create accounts</li>
                                    <li>Browsers (and versions) used to create accounts</li>
                                    <li>Operating systems (and versions) used to create accounts</li>
                                </ol>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <a href="admin-approve.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">5. Approve Candidates</div>
                            </a>
                            <div class="body-1-1">
                                This feature provides a list and details of all candidate profiles not created by WorkAmerica and pending approval. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Approve candidate</li>
                                    <li>Delete candidate</li>
                                    <li>Edit profile (all details)</li>
                                    <li>Edit email & password</li>
                                    <li>Search for specific candidate</li>
                                </ol>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <a href="../VisualizationServlet">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">6. Daily Growth Screen</div>
                            </a>
                            <div class="body-1-1">
                                This feature provides a basic insight into daily profile growth. Numbers are updated automatically (no refresh neccessary). The following information is displayed (for the current day):
                                <ol class="body-1-1">
                                    <li>Total candidates in platform</li>
                                    <li>New candidates</li>
                                    <li>States with new candidate</li>
                                    <li>Manual refresh</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 col-md-offset-1">
                            <a href="admin-profile-import.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">7. Import Candidate Profiles</div>
                            </a>
                            <div class="body-1-1">
                                This feature allows the uploading of a spreadsheet containing candidate profiles, which is parsed and the resulting new profiles are added to the database. A list of all new candidates is displayed after. The following functions are currently available (after the file is uploaded and parsed):
                                <ol class="body-1-1">
                                    <li>Profiles added</li>
                                    <li>Any errors/warnings</li>
                                    <li>Edit profile (all details)</li>
                                    <li>Edit email & password</li>
                                    <li>Delete candidate</li>
                                </ol>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <a href="admin-candidate-logs-list.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">8. Candidate Activity Logs</div>
                            </a>
                            <div class="body-1-1">
                                This feature displays a list of all candidates, along with login information. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Total number of logins</li>
                                    <li>Last login date</li>
                                    <li>View candidate profile</li>
                                </ol>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <a href="admin-client-logs-list.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">9. Employer Activity Logs & Pipeline</div>
                            </a>
                            <div class="body-1-1">
                                This feature displays a list of all employers. Each entry in the list leads to a page displaying login session timestamps, which in turn lead to further information about each login session, as well as the employer's complete pipeline; The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Last login date</li>
                                    <li>Pipeline with all relevant information</li>
                                    <li>Pipeline search</li>
                                    <li>Date + time of every login session</li>
                                    <li>Session duration</li>
                                    <li>Candidates viewed/session</li>
                                    <li>Searches/session: Search Criteria, Keywords, Radius</li>
                                    <li>Pipeline changes/session</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 col-md-offset-1">
                            <a href="admin-new-profile.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">10. Create Candidate</div>
                            </a>
                            <div class="body-1-1">
                                This feature allows the creation of a single candidate profile at a time. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Create candidate</li>
                                    <li>Edit profile (all details)</li>
                                    <li>Edit email & password</li>
                                </ol>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <a href="admin-school-list.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">11. Manage Schools</div>
                            </a>
                            <div class="body-1-1">
                                This feature provides a list and details of all school accounts within the platform. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Edit e-mail & password</li>
                                    <li>Create school</li>
                                    <li>Search for specific school</li>
                                </ol>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <a href="admin-school-logs-list.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">12. School Activity Logs</div>
                            </a>
                            <div class="body-1-1">
                                This feature displays a list of all schools that have logged in. Each entry in the list leads to a list of login dates, which in turn display a list of school actions. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Last login date</li>
                                    <li>All login sessions</li>
                                    <li>Actions per login session</li>
                                    <li>Timestamp and candidate affected per action</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 col-md-offset-1">
                            <a href="../CandidatesPerCriteriaServlet">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">13. Candidates per Criteria</div>
                            </a>
                            <div class="body-1-1">
                                This feature displays the number of candidates per search criteria. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>View number of candidates per certification (obtained and anticipated)</li>
                                    <li>Search through candidates per certification list</li>
                                    <li>View number of candidates per field (past and current)</li>
                                    <li>Search through candidates per field list</li>
                                </ol>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <a href="admin-company-list.jsp">
                                <div class="body-bold-1-1" style="font-size: 18px; text-decoration: underline;">13. Manage Companies</div>
                            </a>
                            <div class="body-1-1">
                                This feature provides a list and details of all companies within the platform. The following functions are currently available:
                                <ol class="body-1-1">
                                    <li>Create company</li>
                                    <li>View list of companies</li>
                                    <li>View employers under a particular company</li>
                                    <li>View profiles viewed company-wide (all time & current month)</li>
                                    <li>Search for a company</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                </div>

                <%@ include file = "/admin/admin-footer.jsp" %>
                    </body>

        </html>