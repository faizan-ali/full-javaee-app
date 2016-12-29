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
            <title>Search Criteria</title>

            <!-- Datatables Stylesheet -->
            <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/t/bs/dt-1.10.11,af-2.1.1,kt-2.1.1,r-2.0.2,sc-1.4.1/datatables.min.css" />

            <%@ include file = "/admin/admin-header.jsp"%>

                <script type="text/javascript" src="https://cdn.datatables.net/t/bs/dt-1.10.11,af-2.1.1,kt-2.1.1,r-2.0.2,sc-1.4.1/datatables.min.js"></script>

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
                            <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right ">Criteria Management</div>
                        </div>
                    </div>
                </div>

                <div class="container-fluid">
                    <div class="row row-centered" style="margin-top: 35px;">
                        <h4 class="archer" style="color: red">${criteriaError}</h4>
                        <h4 class="archer" style="color: green">${criteriaSuccess}</h4>
                    </div>
                    <div class="row row-centered">

                        <!-- Fields Modal -->
                        <div class="modal fade" id="newField" role="dialog">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title archer">New Field</h4>
                                    </div>
                                    <div class="modal-body body-bold-1-1">
                                        <form action="../AdministratorCreateCriteriaServlet" id="fieldForm">
                                            <div class="body-bold-1-1">NAME</div>
                                            <input type="text" class="form-control center-block" name="fieldName" style="background: transparent !important; width: 50% !important; border-bottom: 1px;" />
                                            <input type="hidden" name="field" value="true">
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" form="fieldForm" id="fieldSubmit" class="btn btn-success" data-dismiss="modal">Submit</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Certifications Modal -->
                        <div class="modal fade" id="newCertification" role="dialog">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title archer">New Certification</h4>
                                    </div>
                                    <div class="modal-body body-bold-1-1">
                                        <form action="../AdministratorCreateCriteriaServlet" id="certificationForm">
                                            <div class="body-bold-1-1">NAME</div>
                                            <input type="text" class="form-control center-block" name="certificationName" style="background: transparent !important; width: 50% !important; border-bottom: 1px;" />
                                            <input type="hidden" name="certification" value="true">
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" form="certificationForm" id="certSubmit" class="btn btn-success" data-dismiss="modal">Submit</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Schools Modal -->
                        <div class="modal fade" id="newSchool" role="dialog">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title archer">New School</h4>
                                    </div>
                                    <div class="modal-body body-bold-1-1">
                                        <form action="../AdministratorCreateCriteriaServlet" id="schoolForm">
                                            <div class="body-bold-1-1">NAME</div>
                                            <input type="text" class="form-control center-block" name="schoolName" style="background: transparent !important; width: 50% !important; border-bottom: 1px;" />
                                            <div class="body-bold-1-1">STATE</div>
                                            <input type="text" class="form-control center-block" name="schoolState" style="background: transparent !important; width: 50% !important; border-bottom: 1px;" />
                                            <input type="hidden" name="school" value="true">
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" form="schoolForm" id="schoolSubmit" class="btn btn-success" data-dismiss="modal">Submit</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-5 col-centered">
                            <h4 class="archer text-center">Fields  <button type="button" style="color:green !important;" class="btn btn-default btn-lg icon-plus text-center" data-toggle="modal" data-target="#newField">
                                </button></h4>
                            <table id="fields" class="table table-hover">
                                <thead>
                                    <tr class="body-bold-1-1">
                                        <th>NAME</th>
                                        <th>SUBMIT</th>
                                        <th>DELETE</th>
                                        <th>Name</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="elt" items="${fieldList}">
                                        <tr class="body-bold-1-1">
                                            <form action="../AdministratorCriteriaUpdateServlet">
                                                <td>
                                                    <input type="text" class="input-clean" style="background: transparent !important; width: 100% !important;" value="${elt.name}" name="fieldName" />
                                                </td>
                                                <td>
                                                    <input type="submit" class="btn btn-sm btn-success" value="Submit">
                                                </td>
                                                <td>
                                                    <input type="submit" class="btn btn-sm btn-danger" value="Delete">
                                                </td>
                                                <td>${elt.name}</td>
                                                <input type="hidden" name="fieldID" value="${elt.fieldID}">
                                                <input type="hidden" name="field" value="true">
                                            </form>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="col-md-5 col-md-offset-1 col-centered">
                            <h4 class="archer text-center">Certifications <button type="button" style="color:green !important;" class="btn btn-default btn-lg icon-plus text-center" data-toggle="modal" data-target="#newCertification">
                                </button></h4>
                            <table id="certifications" class="table table-hover">
                                <thead>
                                    <tr class="body-bold-1-1">
                                        <th>NAME</th>
                                        <th>SUBMIT</th>
                                        <th>DELETE</th>
                                        <th>Name</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="elt" items="${certificationList}">
                                        <tr class="body-bold-1-1">
                                            <form action="../AdministratorCriteriaUpdateServlet">
                                                <td>
                                                    <input type="text" class="input-clean" style="background: transparent !important; width: 100% !important;" value="${elt.certification}" name="certificationName" />
                                                </td>
                                                <td>
                                                    <input type="submit" class="btn btn-sm btn-success" value="Submit">
                                                </td>
                                                <td>
                                                    <input type="submit" class="btn btn-sm btn-danger" value="Delete">
                                                </td>
                                                <td>${elt.certification}</td>
                                                <input type="hidden" name="certificationID" value="${elt.certificationID}">
                                                <input type="hidden" name="certification" value="true">
                                            </form>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="row row-centered" style="margin-top: 30px; margin-bottom: 80px;">
                        <div class="col-md-5 col-centered">
                            <h4 class="archer text-center">Schools <button type="button" style="color:green !important;" class="btn btn-default btn-lg icon-plus text-center" data-toggle="modal" data-target="#newSchool">
                                </button></h4>
                            <table id="schools" class="table table-hover">
                                <thead>
                                    <tr class="body-bold-1-1">
                                        <th>NAME</th>
                                        <th>STATE</th>
                                        <th>SUBMIT</th>
                                        <th>DELETE</th>
                                        <th>Name</th>
                                        <th>State</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="elt" items="${schoolList}">
                                        <tr class="body-bold-1-1">
                                            <form action="../AdministratorCriteriaUpdateServlet">
                                                <td>
                                                    <input type="text" class="input-clean" style="background: transparent !important; width: 100% !important;" value="${elt.name}" name="schoolName" />
                                                </td>
                                                <td>
                                                    <input type="text" class="input-clean" style="background: transparent !important; width: 100% !important;" value="${elt.state}" name="schoolState" />
                                                </td>
                                                <td>
                                                    <input type="submit" class="btn btn-sm btn-success" value="Submit">
                                                </td>
                                                <td>
                                                    <input type="submit" class="btn btn-sm btn-danger" value="Delete">
                                                </td>
                                                <td>${elt.name}</td>
                                                <td>${elt.state}</td>
                                                <input type="hidden" name="schoolID" value="${elt.schoolID}">
                                                <input type="hidden" name="school" value="true">
                                            </form>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="col-md-5 col-md-offset-1 col-centered">
                            <h4 class="archer text-center">States</h4>
                            <table id="states" class="table table-hover">
                                <thead>
                                    <tr class="body-bold-1-1">
                                        <th>NAME</th>
                                        <th>ABBRV.</th>
                                        <th>Name</th>
                                        <th>Abbrv.</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="elt" items="${stateList}">
                                        <tr class="body-bold-1-1">
                                            <form action="../AdministratorCriteriaUpdateServlet">
                                                <td>
                                                    <input type="text" class="input-clean" style="background: transparent !important; width: 100% !important;" value="${elt.name}" name="stateName" />
                                                </td>
                                                <td>
                                                    <input type="text" class="input-clean" style="background: transparent !important; width: 100% !important;" value="${elt.abbreviation}" name="stateAbbreviation" />
                                                </td>
                                                <td>${elt.name}</td>
                                                <td>${elt.abbreviation}</td>
                                            </form>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>

                <c:remove var="criteriaError" />
                <c:remove var="criteriaSuccess" />

                <%@ include file = "/admin/admin-footer.jsp" %>
                    </body>
                    <script>
                        $(document).ready(function () {
                            $('.addButton').click(function (evt) {
                                evt.preventDefault();
                                $(this).parent().siblings('li[name="new"]').toggleClass('hide');
                            });
                        });
                    </script>

                    <!-- Datatables Initializer -->
                    <script>
                        $(document).ready(function () {
                            $('#fields').DataTable({
                                "pageLength": 10,
                                "order": [[3, "asc"]],
                                "columnDefs": [
                                    {
                                        "targets": [-1],
                                        "visible": false
                                    }
                                ]
                            });
                            $('#certifications').DataTable({
                                "pageLength": 10,
                                "order": [[3, "asc"]],
                                "columnDefs": [
                                    {
                                        "targets": [-1],
                                        "visible": false
                                    }
                                 ]
                            });
                            $('#schools').DataTable({
                                "pageLength": 10,
                                "order": [[4, "asc"]],
                                "columnDefs": [
                                    {
                                        "targets": [-1],
                                        "visible": false
                                    },
                                    {
                                        "targets": [-2],
                                        "visible": false
                                    }
                                ]
                            });
                            $('#states').DataTable({
                                "pageLength": 10,
                                "order": [[2, "asc"]],
                                "columnDefs": [
                                    {
                                        "targets": [-1],
                                        "visible": false
                            },
                                    {
                                        "targets": [-2],
                                        "visible": false
                                    }
                                 ]
                            });
                        });
                    </script>
                    <script>
                        $(function () {
                            $('body').on('click', '#fieldSubmit', function (e) {
                                $(this.form).submit();
                                $('#newField').modal('hide');
                            });
                            $('body').on('click', '#certSubmit', function (e) {
                                $(this.form).submit();
                                $('#newCertification').modal('hide');
                            });
                            $('body').on('click', '#schoolSubmit', function (e) {
                                $(this.form).submit();
                                $('#newSchool').modal('hide');
                            });
                        })
                    </script>

        </html>