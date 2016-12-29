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
                <title>Company List</title>

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
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right">Company Administration</div>
                            </div>
                        </div>
                    </div>

                <div class="container-fluid" style="margin-bottom: 20px; overflow:scroll !important;">
                        <div class="row row-centered" style="margin-top: 10px; margin-bottom: 10px;">
                            <div class="col-md-11 col-centered">

                                <p class="text-center">
                                    <b>Companies <span class="badge">${fn:length(companyList)}</span></b>
                                    <a href="#" class="addButton" onclick="addCompany();"><img src="../img/plus.png" style="width: 3%"></a>
                                </p>


                                <table id="newCompany" class="table hide" style="margin-top: 15px;">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>View Limit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <form action="../AdministratorCreateCompanyServlet" method = "post">
                                            <tr>
                                                <div class="form-group">
                                                    <td>
                                                        <input type="text" name="name" class="form-control" placeholder="Enter Company Name" required>
                                                    </td>                                            
                                                    <td>
                                                        <input type="text" name="viewLimit" class="form-control" placeholder="Enter 0 for unlimited" required>
                                                    </td>                                                    
                                                    <td>
                                                        <input type="submit" class="btn btn-success" value="Submit">
                                                    </td>
                                                </div>
                                            </tr>
                                        </form>
                                    </tbody>
                                </table>

                                <div class="text-center body-bold-1-1" style="color:red">${companyError}</div>

                                <table id="companyList" class="table table-hover" style="margin-top: 25px;">
                                    <thead>
                                        <tr class = "body-bold-1-1">
                                            <th>Name</th>
                                            <th>Profiles Viewed (all time)</th>
                                            <th>Profiles Viewed (current month)</th>
                                            <th>View Limit</th>
                                            <th>Reffering School (for limit)</th>
                                            <th>Employer Accounts</th>
                                            <th>Submit</th>
                                            <th>NameSearch</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${companyList}" var="company">
                                            <tr class = "body-bold-1-1">
                                                <form action="../AdministratorUpdateCompanyServlet" method = "post">
                                                    <div class="form-group">
                                                        <td>
                                                            <input type="text" class="form-control" name="name" value="${company.name}" required/>
                                                        </td>                                              
                                                        <td>
                                                            <input type="text" name="profilesViewed" class="form-control" value="${company.profilesViewed}" disabled>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="profilesViewedThisMonth" class="form-control" value="${company.profilesViewedThisMonth}" disabled>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="viewLimit" class="form-control" value="${company.viewLimit}" required>
                                                        </td>                                                                                                       
                                                        <td>
                                                            ${company.schoolAccount.school.name}
                                                        </td>
                                                        <td>
                                                           <c:if test = "${empty company.clients}">
                                                               None
                                                           </c:if>
                                                            <c:forEach var = "account" items = "${company.clients}">
                                                                <div>${account.firstName} ${account.lastName}</div>
                                                            </c:forEach>
                                                        </td>                                                 
                                                        <td>
                                                            <input type="hidden" name="companyID" value="${company.companyID}" />
                                                            <input type="submit" class="archer btn btn-success" value="Submit Changes">
                                                        </td>
                                                        <td>${company.name}</td>                                         
                                                    </div>
                                                </form>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>

                <c:remove var="companyError" />

                <%@ include file = "/admin/admin-footer.jsp" %></%@>

                        <script>
                            function addCompany() {
                                $('#newCompany').toggleClass('hide');
                            };
                        </script>

                        <!-- Datatables Initializer -->
                        <script>
                            $(document).ready(function () {
                                $('#companyList').DataTable({
                                    "pageLength": 100,
                                    "order": [[7, "asc"]],
                                    "columnDefs": [
                                        {
                                            "targets": [7],
                                            "visible": false
                                }]
                                });
                            });
                        </script>
                        </body>

            </html>