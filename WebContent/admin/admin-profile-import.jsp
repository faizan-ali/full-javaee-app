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
                <title>Bulk Profile Import</title>

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
                                        <a href="administration.jsp">Back To Main Administration</a>
                                    </div>
                                </div>
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right">Spreadsheet Profile Import</div>
                            </div>
                        </div>
                    </div>

                    <!-- Container for input field and lists/errors -->
                    <div class="container text-center">
                        <div class="row row-centered">

                            <!-- Input field -->
                            <div class="col-md-7 col-centered">

                                <div class="heading-1-1 text-center" style="margin-top: 20px;">Profile Import</div>

                                <div class="body-bold-1-1" id="import-warning">Please ensure that your spreadsheet is in .xlsm format (Excel 2007 onwards), and that it is following the template provided. Make sure that the correct type (School Visit/Fax) is selected, along with the appropriate relevant team member and date the school visit occured/fax was received.</div>

                                <form action="../ProfileImportServlet" method="post" enctype="multipart/form-data" id="spreadsheet-upload">

                                    <div class="row row-centered">
                                        <div class="form-group col-md-3 col-centered" style="margin-bottom: 10px;">
                                            <select name="type" id="" class="form-control">
                                                <option value="School Visit">School Visit</option>
                                                <option value="Fax">Fax</option>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-3 col-md-offset-1 col-centered" style="margin-bottom: 10px;">
                                            <select name="teamMember" id="" class="form-control">
                                                <option value="ryan@workamerica.co">Ryan</option>
                                                <option value="laura@workamerica.co">Laura</option>
                                                <option value="mike@workamerica.co">Mike</option>
                                                <option value="faizan@workamerica.co">Faizan</option>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-3 col-md-offset-1 col-centered">
                                            <input type="text" name="date" class="form-control" placeholder="MM/DD/YYYY">
                                        </div>
                                    </div>

                                    <div class="row row-centered">
                                        <span class="btn candidate-attach-resume-button">
                                    <span class="icon-paper-clip" style="font-size: 15px; color: #959595; margin-right: 4px;"></span>
                                        <span class="field-title-1-1">Upload File</span>
                                        <input type="file" name="spreadsheet" style="width: 100%" onchange="submit();">
                                        </span>
                                    </div>

                                </form>

                                <div class="text-center" style="color: red;">${importError}</div>
                            </div>

                            <!-- Candidates added successfully -->
                            <div class="col-md-6">
                                <h3 class="archer text-center">Successfully Added</h3>
                                <table class="table table-hover" id="successful-table">
                                    <thead>
                                        <tr class="body-bold-1-1">
                                            <th>NAME</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="elt" items="${importedCandidates}">
                                            <tr class="body-bold-1-1">
                                                <td>
                                                    <form action="../AdministratorViewCandidateProfileServlet">
                                                        <a href="#">
                                                            <input type="hidden" name="pipelineJSP" value="false">
                                                            <input type="hidden" name="resultsJSP" value="true">
                                                            <input type="hidden" name="candidateID" value="${elt.candidateID}">
                                                            <input type="submit" name="View Profile" value="${elt.firstName} ${elt.lastName}" style="vertical-align: bottom; overflow: visible; display: inline; margin: 0; padding: 0; border: 0; cursor: pointer; background: none;">
                                                        </a>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Errors -->
                            <div class="col-md-6">
                                <h3 class="archer text-center">Errors</h3>
                                <table class="table table-hover" id="errors-table">
                                    <thead>
                                        <tr class="body-bold-1-1">
                                            <th>NAME</th>
                                            <th>ERROR</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="elt" items="${invalidCandidates}">
                                            <c:if test="${not empty elt.value}">
                                                <tr class="body-1-1">
                                                    <td>${elt.key}</td>
                                                    <td>
                                                        <c:forEach var="innerElt" items="${elt.value}">
                                                            <div class="body-1-1">${innerElt}</div>
                                                        </c:forEach>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <c:remove var="importError" />
                    <%@ include file = "/admin/admin-footer.jsp" %>

                        </body>

            </html>