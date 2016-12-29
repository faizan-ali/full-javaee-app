<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
            <!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
            <html>

            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <!-- The above 3 meta tags must come first in the head; any other head content must come after these tags.
            Scrolling is disabled for this page. -->
                <title>${candidate.firstName} ${candidate.lastName}</title>

                <%@ include file = "/admin/admin-header.jsp"%>

                    <!-- Page-specific CSS -->
                    <link href="../css/candidate-landing.css" rel="stylesheet">

                    <!-- Masked input validation library -->
                    <script src="../js/jquery.maskedinput.min.js" type="text/javascript"></script>

                    <div class="container-fluid platform-nav-container">
                        <div class="row platform-nav">

                            <!-- Desktop nav, hidden from mobile -->
                            <div class="hidden-xs">
                                <div class="col-md-3 platform-nav-back">
                                    <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:#181453;"></div>
                                    <div class="subhead-1-3 col-md-9">
                                        <a href="admin-candidate-list.jsp">Back To Candidate List</a>
                                    </div>
                                </div>
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 platform-nav-button-border pull-right ">${candidate.firstName} ${candidate.lastName}</div>
                                <div class="col-md-2 text-center platform-nav-button subhead-1-2 pull-right "><a href="administration.jsp">Administration Home</a></div>
                            </div>
                        </div>
                    </div>

                    <div class="candidate-profile-background-container">
                        <div class="container candidate-profile-marginator">
                            <div class="row row-centered candidate-profile-container">


                                <!-- Container for profile summary card (edit) -->
                                <div class="col-md-6 col-centered col-xs-12 box-shadow" id="profile-card-edit">

                                    <!-- Container for profile picture and resume attach button -->
                                    <div class="col-md-3 candidate-photo-resume-box-edit remove-horizontal-padding text-center">

                                        <c:choose>
                                            <c:when test="${empty candidate.photo}">
                                                <form action="../PictureUploadServlet" enctype="multipart/form-data" method="post" id="picture-upload">
                                                    <div class="image-upload">
                                                        <label for="file-input">
                                                            <img src="../img/generic-profile-add.png" />
                                                        </label>
                                                        <input name="picture" id="file-input" type="file" onchange="fileSubmit('picture');" />
                                                    </div>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="text-center text-muted archer" style="margin-bottom: 3px;">Click photo to update</div>
                                                <form action="../PictureUploadServlet" enctype="multipart/form-data" method="post">
                                                    <div class="image-upload">
                                                        <label for="file-input">
                                                            <c:choose>
                                                                <c:when test="${empty candidate.photo}">
                                                                    <img src="../img/generic-profile-add.png" alt="Profile Picture">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img src="${pageContext.request.contextPath}/images/${candidate.candidateID}" alt="Profile Picture">
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </label>
                                                        <input type="hidden" name="admin" value="true">
                                                        <input name="picture" id="file-input" type="file" onchange="submit();" />
                                                    </div>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>

                                        <form action="/AdminResumeUploadServlet?candidateID=${candidate.candidateID}&admin=yes" method="post" enctype="multipart/form-data" id="resume-upload">
                                            <span class="btn candidate-attach-resume-button">
                                        <span class="icon-paper-clip" style="font-size: 15px; color: #959595; margin-right: 4px;"></span>
                                            <c:choose>
                                                <c:when test="${empty candidate.resume}">
                                                    <span class="field-title-1-1">Attach Resume</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="field-title-1-1">Update Resume</span>
                                                </c:otherwise>
                                            </c:choose>
                                            <input type="hidden" name="source" value="admin">
                                            <input type="file" name="resume" style="width: 100%" onchange="fileSubmit('resume');">
                                            </span>
                                        </form>
                                        <c:if test="${not empty candidate.resume}">
                                            <form action="../ResumeRetrieverServlet">
                                                <button class="btn candidate-attach-resume-button">
                                                    <span class="icon-paper-clip" style="font-size: 15px; color: #959595; margin-right: 4px;"></span>
                                                    <span class="field-title-1-1">View Resume</span>
                                                    <input type="hidden" name="candidateID" value="${candidate.candidateID }">
                                                    <input type="hidden" name="source" value="admin" />
                                                </button>
                                            </form>
                                        </c:if>
                                        <div class="body-bold-1-1 small" style="color: red;">${adminError}</div>
                                        <div class="body-bold-1-1 small" style="color: green;">${resumeSuccess}</div>

                                    </div>

                                    <!-- Container for profile summary edit within profile card -->
                                    <div class="col-md-8 candidate-summary-box-edit remove-horizontal-padding">
                                        <div class="col-md-6">
                                            <div class="field-title-1-1">First Name</div>
                                            <input type="text" name="firstName" class="body-1-1 input-clean" value="${candidate.firstName}" form="updateForm" id="firstName" />
                                        </div>
                                        <div class="col-md-6">
                                            <div class="field-title-1-1">Last Name</div>
                                            <input type="text" name="lastName" class="body-1-1 input-clean" value="${candidate.lastName}" form="updateForm" id="lastName" />
                                        </div>
                                        <div class="col-md-3">
                                            <div class="field-title-1-1">City</div>
                                            <input type="text" name="city" class="body-1-1 input-clean" value="${candidate.city}" form="updateForm" id="city" />
                                        </div>
                                        <div class="col-md-2" id="states">
                                            <div class="field-title-1-1">State</div>
                                            <input type="hidden" name="state" value="${candidate.state}" form="updateForm">
                                            <select class="multiple-select" id="statesList" name="selectState">
                                                <c:forEach items="${stateList}" var="state">
                                                    <option value="${state.abbreviation}">${state.abbreviation}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="field-title-1-1">ZIP</div>
                                            <input type="number" name="zip" class="body-1-1 input-clean" value="${candidate.zip}" form="updateForm" id="zip" maxlength="5">
                                        </div>
                                        <div class="col-md-5">
                                            <div class="field-title-1-1">Email Address</div>
                                            <input type="email" name="email" class="body-1-1 input-clean" value="${candidate.email}" form="updateForm" id="email" style="width: 100%;" />
                                        </div>
                                        <div class="col-md-6">
                                            <div class="field-title-1-1">Phone Number</div>
                                            <input type="text" name="phone" class="body-1-1 input-clean" value="${candidate.phone}" form="updateForm" id="phone" />
                                        </div>

                                        <div class="col-md-6">
                                            <div class="field-title-1-1">Alternate Phone Number</div>
                                            <input type="text" name="alternatePhone" class="body-1-1 input-clean" value="${candidate.alternatePhone}" form="updateForm" id="alternatePhone" />
                                        </div>

                                        <div class="col-md-12" id="schools">
                                            <div class="field-title-1-1">Current School</div>
                                            <input type="hidden" name="school" value="${candidate.school}" form="updateForm">
                                            <select data-placeholder="Start typing" class="multiple-select" id="schoolsList" name="selectSchool">
                                                <c:forEach items="${schoolList}" var="school">
                                                    <option value="${school.name}">${school.name}</option>
                                                </c:forEach>
                                                <option value="Other">Other</option>
                                                <option value="Not currently enrolled">Not currently enrolled</option>
                                            </select>
                                        </div>

                                        <div class="col-md-12" style="margin-bottom: 5px;">
                                            <div class="field-title-1-1">Password</div>
                                            <input type="text" name="password" class="body-1-1 input-clean" placeholder="Minimum 4 characters" form="updateForm">
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <div class="row row-centered">


                                <!-- Container for candidate details (edit) -->
                                <div class="col-md-6 col-centered col-xs-12 candidate-detail-container" id="candidate-detail-edit">

                                    <!-- Form for candidate profile information -->
                                    <form action="../AdministratorCandidateUpdateServlet" id="updateForm" name="candidate.orm" onsubmit="return removeLocalItems()">
                                        <div class="row">
                                            <div class="col-xs-12" id="fields">
                                                <div class="body-bold-1-1">1.What type of training are you pursuing most recently? *</div>
                                                <input type="hidden" name="field" value="${candidate.field}" form="updateForm">
                                                <select data-placeholder="Start typing" class="multiple-select" name="fieldSelect" id="fieldsList" multiple="multiple">
                                                    <c:forEach items="${fieldList}" var="field">
                                                        <option value="${field.name}">${field.name}</option>
                                                    </c:forEach>
                                                    <option value="Other">Other</option>
                                                </select>
                                                <div class="detail-1-1">If you select"Other", please explain in the answer to question 9.</div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="body-bold-1-1">2. When do you complete your most recent training? *</div>
                                                <input type="text" name="completionDate" value="${candidate.completionDate}" class="input-clean" id="date" data-date-format="mm/dd/yy" readonly>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12" id="certifications">
                                                <div class="body-bold-1-1">3. What certification does your most recent training lead to? *</div>
                                                <input type="hidden" name="certification" value="${candidate.anticipatedCertification}" form="updateForm">
                                                <select data-placeholder="Start typing" class="multiple-select" id="certificationsList" multiple="multiple">
                                                    <c:forEach items="${certificationList}" var="certification">
                                                        <option value="${certification.certification}">${certification.certification}</option>
                                                    </c:forEach>
                                                    <option value="Other">Other</option>
                                                </select>
                                                <div class="detail-1-1">If you select"Other", please explain in the answer to question 9.</div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12" id="pastFields">
                                                <div class="body-bold-1-1">4. What type of training have you pursued in the past? *</div>
                                                <input type="hidden" name="pastField" value="${candidate.pastField}" form="updateForm">
                                                <select data-placeholder="Start typing" class="multiple-select" id="pastFieldsList" multiple="multiple">
                                                    <option value="None">None</option>
                                                    <c:forEach items="${fieldList}" var="field">
                                                        <option value="${field.name}">${field.name}</option>
                                                    </c:forEach>
                                                    <option value="Other">Other</option>
                                                </select>
                                                <div class="detail-1-1">If you select"Other", please explain in the answer to question 9.</div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12" id="obtainedCertifications">
                                                <div class="body-bold-1-1">5. What certifications have you obtained in the past? *</div>
                                                <input type="hidden" name="obtainedCertification" value="${candidate.obtainedCertification}" form="updateForm">
                                                <select data-placeholder="Start typing" class="multiple-select" id="pastCertificationsList" multiple="multiple">
                                                    <option value="None">None</option>
                                                    <c:forEach items="${certificationList}" var="certification">
                                                        <option value="${certification.certification}">${certification.certification}</option>
                                                    </c:forEach>
                                                    <option value="Other">Other</option>
                                                </select>
                                                <div class="detail-1-1">If you select"Other", please explain in the answer to question 9.</div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12" id="pastEducations">
                                                <div class="body-bold-1-1">6. What type of education have you completed? *</div>
                                                <input type="hidden" name="pastEducation" value="${candidate.pastEducation}" form="updateForm">
                                                <select data-placeholder="Start typing" class="multiple-select" id="pastEducationList" multiple="multiple">
                                                    <option value="Some high school; no diploma">Some high school; no diploma</option>
                                                    <option value="High school diploma or GED">High school diploma or GED</option>
                                                    <option value="Some trade/technical/vocational training; no certificate">Some trade/technical/vocational training; no certificate</option>
                                                    <option value="Completed trade/technical/vocational certificate or diploma">Completed trade/technical/vocational certificate or diploma</option>
                                                    <option value="Some college credit; no degree">Some college credit; no degree</option>
                                                    <option value="Associates degree">Associates degree</option>
                                                    <option value="Bachelors degree or more">Bachelors degree or more</option>
                                                    <option value=""></option>
                                                </select>
                                                <div class="detail-1-1">Select highest level</div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="body-bold-1-1">7. How much relevant work experience do you have? *</div>
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${candidate.workExperience eq 'No relevant work experience'}">
                                                                <input type="radio" name="workExperience" value="No relevant work experience" checked>No relevant work experience
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="workExperience" value="No relevant work experience">No relevant work experience
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${candidate.workExperience eq 'Less than 1 year of work experience'}">
                                                                <input type="radio" name="workExperience" value="Less than 1 year of work experience" checked>Less than 1 year of work experience
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="workExperience" value="Less than 1 year of work experience">Less than 1 year of work experience
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${candidate.workExperience eq '1-3 years of work experience'}">
                                                                <input type="radio" name="workExperience" value="1-3 years of work experience" checked>1-3 years of work experience
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="workExperience" value="1-3 years of work experience">1-3 years of work experience
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${candidate.workExperience eq '3-5 years of work experience'}">
                                                                <input type="radio" name="workExperience" value="3-5 years of work experience" checked>3-5 years of work experience
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="workExperience" value="3-5 years of work experience">3-5 years of work experience
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${candidate.workExperience eq 'More than 5 years of work experience'}">
                                                                <input type="radio" name="workExperience" value="More than 5 years of work experience" checked>More than 5 years of work experience
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="workExperience" value="More than 5 years of work experience">More than 5 years of work experience
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-6 col-xs-6">
                                                <div class="body-bold-1-1">8. Are you a veteran?</div>
                                                <div class="radio-inline">
                                                    <label class="body-1-1">
                                                        <c:choose>
                                                            <c:when test="${candidate.veteran eq 'Yes'}">
                                                                <input type="radio" name="veteran" value="Yes" checked />Yes
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="veteran" value="Yes" />Yes
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                                <div class="radio-inline">
                                                    <label class="body-1-1">
                                                        <c:choose>
                                                            <c:when test="${candidate.veteran eq 'No'}">
                                                                <input type="radio" name="veteran" value="No" checked/> No
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="veteran" value="No" /> No
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-xs-6">
                                                <div class="body-bold-1-1">9. Are you currently employed?</div>
                                                <div class="radio-inline">
                                                    <label class="body-1-1">
                                                        <c:choose>
                                                            <c:when test="${candidate.employed eq 'Yes'}">
                                                                <input type="radio" name="employed" value="Yes" checked/> Yes
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="employed" value="Yes" /> Yes
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                                <div class="radio-inline">
                                                    <label class="body-1-1">
                                                        <c:choose>
                                                            <c:when test="${candidate.employed eq 'No'}">
                                                                <input type="radio" name="employed" value="No" checked/> No
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="employed" value="No" /> No
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="body-bold-1-1">10. Are you willing to relocate/move for your career?</div>
                                                <div class="radio-inline">
                                                    <label class="body-1-1">
                                                        <c:choose>
                                                            <c:when test="${candidate.relocate eq 'Yes'}">
                                                                <input type="radio" name="relocate" value="Yes" checked/> Yes
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="relocate" value="Yes" /> Yes
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                                <div class="radio-inline">
                                                    <label class="body-1-1">
                                                        <c:choose>
                                                            <c:when test="${candidate.relocate eq 'No'}">
                                                                <input type="radio" name="relocate" value="No" checked/> No
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="relocate" value="No" /> No
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="body-bold-1-1">11. What more should employers know about your work history and employment goals?</div>
                                                <textarea name="additionalInformation" id="" cols="35" rows="3" class="input-clean">${candidate.additionalInformation}</textarea>
                                                <div class="detail-1-1">List additional certificates, skills, revelant work experience and accomplishments. Write what you are looking for as a career field, how far are you willing to travel, etc.</div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="body-bold-1-1">12. Do you have a valid driver's license?</div>
                                                <div class="radio-inline">
                                                    <label class="body-1-1">
                                                        <c:choose>
                                                            <c:when test="${candidate.validDriversLicense eq 'Yes'}">
                                                                <input type="radio" name="validDriversLicense" value="Yes" checked/> Yes
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="validDriversLicense" value="Yes" /> Yes
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                                <div class="radio-inline">
                                                    <label class="body-1-1">
                                                        <c:choose>
                                                            <c:when test="${candidate.validDriversLicense eq 'No'}">
                                                                <input type="radio" name="validDriversLicense" value="No" checked/> No
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name="validDriversLicense" value="No" /> No
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Submit button -->
                                        <div class="row text-center" style="border:none;">
                                            <div class="col-md-6 candidate-profile-submit-button text-center" id="submit-button">
                                                <input type="hidden" name="candidateID" value="${candidate.candidateID}" />
                                                <button type="submit" class="btn btn-link" form="updateForm" onclick="candidateUpdate();">Submit My Profile</button>
                                            </div>
                                            <p class="hidden body-bold-1-1" id="incompleteError" style="color: red; margin-top: 10px;">Please fill out the highlighted sections</p>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>


                    <%@ include file = "/admin/admin-footer.jsp" %>

                        <!-- Datepicker JS -->
                        <script src="../js/bootstrap-datepicker.js" type="text/javascript"></script>

                        <!-- Datepicker Initialization -->
                        <script>
                            $('#date').datepicker();
                        </script>

                        <script>
                            function fileSubmit(type) {

                                if (type == 'resume') {
                                    document.getElementById('resume-upload').submit();
                                } else {
                                    document.getElementById('picture-upload').submit();
                                }
                            }
                        </script>


                        <!-- Populates Select2 defaults with pre-existing values pulled from Candidate profile -->
                        <script>
                            window.onload = function () {

                                if ($("input[name=school]").val().length > 2) {
                                    $("#schoolsList").select2().val($("input[name=school]").val()).trigger("change");
                                } else {
                                    $("#schoolsList").select2().val(" ").trigger("change");
                                }

                                if ($("input[name=state]").val().length > 1) {
                                    $("#statesList").select2().val($("input[name=state]").val()).trigger("change");
                                } else {
                                    $("#statesList").select2().val(" ").trigger("change");
                                }

                                fields = "";
                                fieldArray = $("input[name=field]").val().split(",");
                                $("#fieldsList").select2().val(fieldArray).trigger("change");

                                pastFields = "";
                                pastFieldArray = $("input[name=pastField]").val().split(",");
                                $("#pastFieldsList").select2().val(pastFieldArray).trigger("change");

                                certifications = "";
                                certificationArray = $("input[name=certification]").val().split(",");
                                $("#certificationsList").select2().val(certificationArray).trigger("change");

                                pastCertifications = "";
                                pastCertificationArray = $("input[name=obtainedCertification]").val().split(",");
                                $("#pastCertificationsList").select2().val(pastCertificationArray).trigger("change");

                                pastEducation = "";
                                pastEducationArray = $("input[name=pastEducation]").val().split(",");
                                $("#pastEducationList").select2().val(pastEducationArray).trigger("change");
                            };
                        </script>

                        <!-- Phone validation -->
                        <script>
                            jQuery(function ($) {
                                $("#phone").mask("(999) 999-9999");
                                $("#alternatePhone").mask("(999) 999-9999");
                            })
                        </script>

                        <!-- Pulls selected data from Select2 lists and adds it to hidden input fields which are then sent to POST -->
                        <script>
                            function candidateUpdate() {

                                var titles = []
                                var arr = $('#schools span[title]');
                                for (var i = 0; i < arr.length; i++) {
                                    titles.push(arr[i].title);
                                };
                                var string = titles.toString();

                                $("[name='school']")[0].setAttribute("value", string);

                                titles = []
                                arr = $('#states span[title]');
                                for (var i = 0; i < arr.length; i++) {
                                    titles.push(arr[i].title);
                                };
                                string = titles.toString();

                                $("[name = 'state']")[0].setAttribute("value", string);

                                titles = []
                                arr = $('#fields li[title]');
                                for (var i = 0; i < arr.length; i++) {
                                    titles.push(arr[i].title);
                                };
                                string = titles.toString();

                                $("[name='field']")[0].setAttribute("value", string);

                                titles = []
                                arr = $('#certifications li[title]');
                                for (var i = 0; i < arr.length; i++) {
                                    titles.push(arr[i].title);
                                };
                                string = titles.toString();

                                $("[name='certification']")[0].setAttribute("value", string);

                                titles = []
                                arr = $('#pastFields li[title]');
                                for (var i = 0; i < arr.length; i++) {
                                    titles.push(arr[i].title);
                                };
                                string = titles.toString();

                                $("[name='pastField']")[0].setAttribute("value", string);

                                titles = []
                                arr = $('#obtainedCertifications li[title]');
                                for (var i = 0; i < arr.length; i++) {
                                    titles.push(arr[i].title);
                                };
                                string = titles.toString();

                                $("[name='obtainedCertification']")[0].setAttribute("value", string);

                                titles = []
                                arr = $('#pastEducations li[title]');
                                for (var i = 0; i < arr.length; i++) {
                                    titles.push(arr[i].title);
                                };
                                string = titles.toString();

                                $("[name='pastEducation']")[0].setAttribute("value", string);

                            }
                        </script>

                        <!-- Datepicker Initialization -->
                        <script>
                            $('#date').datepicker();
                        </script>

                        <c:remove var="resumeError" />
                        <c:remove var="resumeSuccess" />
                        </body>

            </html>