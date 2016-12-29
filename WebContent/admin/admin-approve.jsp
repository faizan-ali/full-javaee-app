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
            <title>Approve Candidates</title>

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
                                    <a href="administration.jsp">Back To Administration</a>
                                </div>
                            </div>
                            <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right ">Approve Candidates</div>
                        </div>
                    </div>
                </div>

                <div class="container-fluid" style="margin-top: 25px;  overflow:scroll !important;">
                    <div class="row" style="margin-bottom: 55px;">
                        <div class="col-md-12">
                            <table id="list" class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Candidate</th>
                                        <th>Date Created</th>
                                        <th>City, State</th>
                                        <th>School</th>
                                        <th>Curr. Field</th>
                                        <th>Ant. Cert.</th>
                                        <th>Completion</th>
                                        <th>Past Field</th>
                                        <th>Obt. Cert.</th>
                                        <th>E-mail</th>
                                        <th>Phone</th>
                                        <th>Alt. Phone</th>
                                        <th>Veteran</th>
                                        <th>Add. Info.</th>
                                        <th>Resume</th>
                                        <th>Work Exp.</th>
                                        <th>Employed</th>
                                        <th>Relocate</th>
                                        <th>Past Ed.</th>
                                        <th>Photo</th>
                                        <th>Authorized</th>
                                        <th>Approve</th>
                                        <th>Delete</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="elt" items="${approvalList}">
                                        <tr>
                                            <td>
                                                <form action="../AdministratorViewCandidateProfileServlet">
                                                    <a href="#">
                                                        <input type="hidden" name="admin" value="true">
                                                        <input type="hidden" name="candidateID" value="${elt.candidateID }">
                                                        <input type="submit" name="View Profile" value="${elt.firstName} ${elt.lastName}" style="vertical-align: bottom; overflow: visible; display: inline; margin: 0; padding: 0; border: 0; cursor: pointer; background: none;">
                                                    </a>
                                                </form>
                                            </td>
                                            <td>${elt.dateCreated}</td>
                                            <td>${elt.city}, ${elt.state}</td>
                                            <td>${elt.school}</td>
                                            <td>${elt.field}</td>
                                            <td>${elt.anticipatedCertification}</td>
                                            <td>${elt.completionDate}</td>
                                            <td>${elt.pastField}</td>
                                            <td>${elt.obtainedCertification}</td>
                                            <td>${elt.email}</td>
                                            <td>${elt.phone}</td>
                                            <td>${elt.alternatePhone}</td>
                                            <td>${elt.veteran}</td>
                                            <td>${elt.additionalInformation}</td>
                                            <td>
                                                <c:if test="${not empty elt.resume}">
                                                    Yes
                                                </c:if>
                                            </td>
                                            <td>${elt.workExperience}</td>
                                            <td>${elt.employed}</td>
                                            <td>${elt.relocate}</td>
                                            <td>${elt.pastEducation}</td>
                                            <td>
                                                <c:if test="${not empty elt.photo}">
                                                    Yes
                                                </c:if>
                                            </td>
                                            <td>${elt.authorized}</td>
                                            <td>
                                                <form action="../AdministratorApproveCandidateServlet">
                                                    <input type="hidden" name="candidateID" value="${elt.candidateID }">
                                                    <button type="submit" class="btn btn-success">Approve</button>
                                                </form>
                                            </td>
                                            <td>
                                                <form action="../AdministratorDeleteCandidateServlet">
                                                    <input type="hidden" name="approveList" value="true">
                                                    <input type="hidden" name="candidateID" value="${elt.candidateID }">
                                                    <button type="submit" class="btn btn-danger">Delete</button>
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
                        $(document).ready(function () {
                            $('#list').DataTable({
                                "pageLength": 25,
                                "order": [[1, "des"]]
                            });
                        });
                    </script>

                    </body>

        </html>