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
                    <title>School List</title>

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
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right">School Administration</div>
                            </div>
                        </div>
                    </div>

                    <div class="container-fluid" style="margin-bottom: 20px;">
                        <div class="row row-centered" style="margin-top: 10px; margin-bottom: 10px;">
                            <div class="col-md-11 col-centered">

                                <p class="text-center">
                                    <b>School Accounts <span class="badge">${fn:length(schoolAccountList)}</span></b>
                                    <a href="#" class="addButton" onclick="addSchool();"><img src="../img/plus.png" style="width: 3%"></a>
                                </p>


                                <table id="newSchool" class="table hide" style="margin-top: 15px;">
                                    <thead>
                                        <tr>
                                            <th>School</th>
                                            <th>E-mail</th>
                                            <th>Password</th>
                                            <th>Employer Code</th>
                                            <th>Submit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <form action="../AdministratorCreateSchoolAccountServlet" method="post">
                                            <tr>
                                                <div class="form-group">
                                                    <td>
                                                        <select class = "form-control" name="school" placeholder = "Select school" required>
                                                           <c:forEach var = "elt" items = "${schoolList}">
                                                            <option value="${elt.schoolID}">${elt.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <input type="email" name="email" class="form-control" placeholder="Enter E-mail Address" required>
                                                    </td>
                                                    <td>
                                                        <input type="text" name="password" class="form-control" placeholder="Enter Password" required/>
                                                    </td>
                                                    <td>
                                                        <input type="text" name = "code" class = "form-control" placeholder = "Enter code" required>
                                                    </td>
                                                    <td>
                                                        <input type="submit" class="btn btn-success" value="Submit">
                                                    </td>
                                                </div>
                                            </tr>
                                        </form>
                                    </tbody>
                                </table>

                                <div class="text-center body-bold-1-1" style="color:red">${schoolError}</div>

                                <table id="schoolList" class="table table-hover" style="margin-top: 25px;">
                                    <thead>
                                        <tr>
                                            <th>School</th>
                                            <th>E-mail</th>
                                            <th>Password</th>
                                            <th>Employer Code</th>
                                            <th>Submit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${schoolAccountList}" var="account">
                                            <tr>
                                                <form action="../AdministratorSchoolAccountUpdateServlet">
                                                    <div class="form-group">
                                                        <td>
                                                            ${account.school.name}
                                                        </td>
                                                        <td>
                                                            <input type="email" name="email" class="form-control" placeholder="Enter E-mail Address" value = "${account.email}" required>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="password" class="form-control" placeholder="Enter Password" />
                                                        </td>
                                                        <td>
                                                            ${account.employerCode}
                                                        </td>
                                                        <td>
                                                           <input type="hidden" value = "${account.schoolAccountID}" name = "schoolAccountID">
                                                            <input type="submit" class="btn btn-success" value="Submit">
                                                        </td>
                                                    </div>
                                                </form>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>

                    <c:remove var="schoolError" />

                    <%@ include file = "/admin/admin-footer.jsp" %></%@>

                    <script>
                        function addSchool() {
                            $('#newSchool').toggleClass('hide');
                        };
                    </script>

                    <!-- Datatables Initializer -->
                    <script>
                        $(document).ready(function () {
                            $('#schoolList').DataTable({
                                "order": [[0, "asc"]],
                            });
                        });
                    </script>
                    </body>

            </html>