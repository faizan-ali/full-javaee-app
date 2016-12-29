<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
            <!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
            <html>

            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags.
            Scrolling is disabled for this page. -->
                <title>Employer List</title>

                <!-- Datatables Stylesheet -->
                <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.css" />


                <%@ include file = "/admin/admin-header.jsp"%></%@>

                    <!-- Datatables JS -->
                    <script type="text/javascript" src="https://cdn.datatables.net/s/bs-3.3.5/jszip-2.5.0,pdfmake-0.1.18,dt-1.10.10,af-2.1.0,b-1.1.0,b-colvis-1.1.0,b-html5-1.1.0,b-print-1.1.0,cr-1.3.0,fc-3.2.0,fh-3.1.0,r-2.0.0,sc-1.4.0/datatables.min.js"></script>


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
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right">Client Administration</div>
                            </div>
                        </div>
                    </div>

                <div class="container-fluid" style="margin-bottom: 20px; overflow:scroll !important;">
                        <div class="row row-centered" style="margin-top: 10px; margin-bottom: 10px;">
                            <div class="col-md-11 col-centered">

                                <p class="text-center">
                                    <b>Employers <span class="badge">${fn:length(clientList)}</span></b>
                                    <a href="#" class="addButton" onclick="addClient();"><img src="../img/plus.png" style="width: 3%"></a>
                                </p>


                                <table id="newClient" class="table hide" style="margin-top: 15px;">
                                    <thead>
                                        <tr>
                                            <th>First Name</th>
                                            <th>Last Name</th>
                                            <th>Company</th>
                                            <th>E-mail</th>
                                            <th>Password</th>
                                            <th>Profile View Limit</th>
                                            <th>Geographical Limit</th>
                                            <th>Assign Number?</th>
                                            <th>Submit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <form action="../AdministratorCreateClientServlet" method = "post">
                                            <tr>
                                                <div class="form-group">
                                                    <td>
                                                        <input type="text" name="firstName" class="form-control" placeholder="Enter First Name" required>
                                                    </td>
                                                    <td>
                                                        <input type="text" name="lastName" class="form-control" placeholder="Enter Last name" required>
                                                    </td>
                                                    <td>
                                                        <select name="companyID" class = "form-control" required>
                                                            <c:forEach var = "elt" items = "${companyList}">
                                                                <option value="${elt.companyID}">${elt.name}</option>
                                                            </c:forEach>
                                                        </select>  
                                                        <input type="text" name="company" class="form-control" placeholder="Enter Company Name" required>
                                                    </td>
                                                    <td>
                                                        <input type="email" name="email" class="form-control" placeholder="Enter E-mail Address" required>
                                                    </td>
                                                    <td>
                                                        <input type="text" name="password" class="form-control" placeholder="Enter Password" required/>
                                                    </td>
                                                    <td>
                                                        <input type="text" name="viewLimit" class="form-control" placeholder="Enter 0 for unlimited" required>
                                                    </td>
                                                    <td>
                                                        <input type="text" name="geoLimit" class="form-control" placeholder="Comma seperated, All for all" required>
                                                    </td>
                                                    <td>
                                                        <select type="text" name = "assignNumber" class = "form-control" required>
                                                            <option value="Yes">Yes</option>
                                                            <option value="No">No</option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <input type="submit" class="btn btn-success" value="Submit">
                                                    </td>
                                                </div>
                                            </tr>
                                        </form>
                                    </tbody>
                                </table>

                                <div class="text-center body-bold-1-1" style="color:red">${clientError}</div>

                                <table id="clientList" class="table table-hover" style="margin-top: 25px;">
                                    <thead>
                                        <tr class = "body-bold-1-1">
                                            <th>First Name</th>
                                            <th>Last Name</th>
                                            <th>Company</th>
                                            <th>E-mail</th>
                                            <th>Profiles Viewed (All time)</th>
                                            <th>Profiles Viewed (This month)</th>
                                            <th>Profile View Limit</th>
                                            <th>Geographical Limit</th>
                                            <th>Password</th>
                                            <th>Approved</th>
                                            <th>School Limit</th>
                                            <th>Assigned Number</th>
                                            <th>Submit</th>
                                            <th>FirstName</th>
                                            <th>LastName</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${clientList}" var="client">
                                            <tr class = "body-bold-1-1">
                                                <form action="../AdministratorClientUpdateServlet" method = "post">
                                                    <div class="form-group">
                                                        <td>
                                                            <input type="text" class="form-control" name="firstName" value="${client.firstName}" required/>
                                                        </td>
                                                        <td>
                                                            <input type="text" class="form-control" name="lastName" value="${client.lastName}" required/>
                                                        </td>
                                                        <td>
                                                            <select name="companyID" required class = "form-control">
                                                            <option selected value="${client.companyID}">${client.company.name}</option>
                                                            <c:forEach var = "elt" items = "${companyList}">
                                                                <option value="${elt.companyID}">${elt.name}</option>
                                                            </c:forEach>
                                                        </select>                            
                                                        </td>
                                                        <td>
                                                            <input type="email" name="email" class="form-control" value="${client.email}" required/>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="profilesViewed" class="form-control" value="${client.profilesViewed}" disabled>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="profilesViewedThisMonth" class="form-control" value="${client.profilesViewedThisMonth}" disabled>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="viewLimit" class="form-control" value="${client.viewLimit}" required>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="geoLimit" class="form-control" value="${client.geoLimit}" placeholder="Comma seperated, All for all" required>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="password" class="form-control" placeholder="Minimum 4 characters">
                                                        </td>
                                                        <td>
                                                            <select name="approved" id="" class = "form-control" required>
                                                                <option value="${client.approved}">${client.approved}</option>
                                                                <option value="Yes">Yes</option>
                                                                <option value="No">No</option>
                                                            </select>
                                                        </td>
                                                        <td>
                                                            ${client.schoolAccount.school.name}
                                                        </td>
                                                        <td>
                                                            ${client.assignedNumber}
                                                        </td>
                                                        <td>
                                                            <input type="hidden" name="clientID" value="${client.clientID}" />
                                                            <input type="submit" class="btn btn-success" value="Submit Changes">
                                                        </td>
                                                        <td>${client.firstName}</td>
                                                        <td>${client.lastName}</td>
                                                    </div>
                                                </form>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>

                <c:remove var="clientError" />

                <%@ include file = "/admin/admin-footer.jsp" %></%@>

                        <script>
                            function addClient() {
                                $('#newClient').toggleClass('hide');
                            };
                        </script>

                        <!-- Datatables Initializer -->
                        <script>
                            $(document).ready(function () {
                                $('#clientList').DataTable({
                                    "order": [[5, "asc"]],
                                    "columnDefs": [
                                        {
                                            "targets": [13],
                                            "visible": false
                                },
                                {
                                            "targets": [14],
                                            "visible": false
                                }]
                                });
                            });
                        </script>
                        </body>

            </html>