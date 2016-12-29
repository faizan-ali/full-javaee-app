<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

            <!-- Container for profile summary card (edit) -->
            <div class="col-md-6 col-xs-12 box-shadow hidden" id="profile-card-edit">

                <!-- Container for profile picture and resume attach button -->
                <div class="col-md-3 candidate-photo-resume-box-edit remove-horizontal-padding text-center">

                    <c:choose>
                        <c:when test="${empty user.photo}">
                            <form action="PictureUploadServlet" enctype="multipart/form-data" method="post" id="picture-upload">
                                <div class="image-upload">
                                    <label for="file-input">
                                        <img src="img/generic-profile-add.png" />
                                    </label>
                                    <input name="picture" id="file-input" type="file" onchange="fileSubmit('picture');" />
                                </div>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="PictureUploadServlet" enctype="multipart/form-data" method="post">
                                <div class="image-upload">
                                    <label for="file-input">
                                        <img src="img/generic-profile-update.png" />
                                    </label>
                                    <input name="picture" id="file-input" type="file" onchange="submit();" />
                                </div>
                            </form>
                        </c:otherwise>
                    </c:choose>

                    <form action="ResumeUploadServlet" method="post" enctype="multipart/form-data" id="resume-upload">
                        <span class="btn candidate-attach-resume-button"><span class="icon-paper-clip" style="font-size: 15px; color: #959595; margin-right: 4px;"></span>
                        <c:choose>
                            <c:when test="${empty user.resume}">
                                <span class="field-title-1-1">Attach Resume</span>
                            </c:when>
                            <c:otherwise>
                                <span class="field-title-1-1">Update Resume</span>
                            </c:otherwise>
                        </c:choose>
                        <input type="file" name="resume" style="width: 100%" onchange="fileSubmit('resume');">
                        <input type="hidden" name="source" value="candidate" />
                        </span>
                    </form>
                    <div class="body-bold-1-1 small" style="color: red;">${resumeError}</div>
                    <div class="body-bold-1-1 small" style="color: green;">${resumeSuccess}</div>

                </div>

                <!-- Container for profile summary edit within profile card -->
                <div class="col-md-8 candidate-summary-box-edit remove-horizontal-padding">
                    <div class="col-md-6">
                        <div class="field-title-1-1">First Name</div>
                        <input type="text" name="firstName" id="firstName" class="body-1-1 input-clean" value="${user.firstName}" form="updateForm" id="firstName" />
                    </div>
                    <div class="col-md-6">
                        <div class="field-title-1-1">Last Name</div>
                        <input type="text" name="lastName" class="body-1-1 input-clean" value="${user.lastName}" form="updateForm" id="lastName" />
                    </div>
                    <div class="col-md-3">
                        <div class="field-title-1-1">City</div>
                        <input type="text" name="city" class="body-1-1 input-clean" value="${user.city}" form="updateForm" id="city" />
                    </div>
                    <div class="col-md-2" id="states">
                        <div class="field-title-1-1">State</div>
                        <input type="hidden" name="state" value="${user.state}" form="updateForm">
                        <select class="multiple-select" id="statesList" name="selectState">
                            <c:forEach items="${stateList}" var="state">
                                <option value="${state.abbreviation}">${state.abbreviation}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <div class="field-title-1-1">ZIP</div>
                        <input type="number" name="zip" class="body-1-1 input-clean" value="${user.zip}" form="updateForm" id="zip" maxlength="5">
                    </div>
                    <div class="col-md-5">
                        <div class="field-title-1-1">Email Address</div>
                        <input type="email" name="email" class="body-1-1 input-clean" value="${user.email}" form="updateForm" id="email" style="width: 100%;" />
                    </div>
                    <div class="col-md-6">
                        <div class="field-title-1-1">Phone Number</div>
                        <input type="text" name="phone" class="body-1-1 input-clean" value="${user.phone}" form="updateForm" id="phone" />
                    </div>

                    <div class="col-md-6">
                        <div class="field-title-1-1">Alternate Phone Number</div>
                        <input type="text" name="alternatePhone" class="body-1-1 input-clean" value="${user.alternatePhone}" form="updateForm" id="alternatePhone" />
                    </div>

                    <div class="col-md-12" id="schools">
                        <div class="field-title-1-1">Current School</div>
                        <input type="hidden" name="school" value="${user.school}" form="updateForm">
                        <select class="form-control" id="schoolsList" name="selectSchool">
                            <option value="${user.school}" selected>${user.school}</option>
                            <c:forEach items="${schoolList}" var="school">
                                <option value="${school.name}">${school.name}</option>
                            </c:forEach>
                            <option value="Other">Other</option>
                            <option value="Not currently enrolled">Not currently enrolled</option>
                        </select>
                    </div>
                </div>

            </div>

            <!-- Container for candidate details (edit) -->
            <div class="col-md-6 col-xs-12 candidate-detail-container hidden" id="candidate-detail-edit">

                <!-- Form for candidate profile information -->
                <form action="CandidateProfileUpdateServlet" id="updateForm" name="userForm" onsubmit="return validate();">
                    <div class="row">
                        <div class="col-xs-12" id="fields">
                            <div class="body-bold-1-1">1. What type of training are you pursuing most recently? *</div>
                            <input type="hidden" name="field" value="${user.field}" form="updateForm">
                            <select data-placeholder="Start typing" class="multiple-select" name="fieldSelect" id="fieldsList" multiple="multiple">
                                <c:forEach items="${fieldList}" var="field">
                                    <option value="${field.name}">${field.name}</option>
                                </c:forEach>
                                <option value="Other">Other - Explain in Q12</option>
                            </select>
                            <div class="detail-1-1">If you select"Other", please explain in the answer to question 12</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="body-bold-1-1">2. When do you complete your most recent training? *</div>
                            <input type="text" name="completionDate" value="${user.completionDate}" class="input-clean" id="date" data-date-format="mm/dd/yy" readonly>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12" id="certifications">
                            <div class="body-bold-1-1">3. What certifications, licenses or credentials does this training lead to? *</div>
                            <input type="hidden" name="certification" value="${user.anticipatedCertification}" form="updateForm">
                            <select data-placeholder="Start typing" class="multiple-select" id="certificationsList" multiple="multiple">
                                <c:forEach items="${certificationList}" var="certification">
                                    <option value="${certification.certification}">${certification.certification}</option>
                                </c:forEach>
                                <option value="Other">Other - Explain in Q12</option>
                            </select>
                            <div class="detail-1-1">If you select"Other", please explain in the answer to question 12</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12" id="pastFields">
                            <div class="body-bold-1-1">4. What type of training have you pursued in the past? *</div>
                            <input type="hidden" name="pastField" value="${user.pastField}" form="updateForm">
                            <select data-placeholder="Start typing" class="multiple-select" id="pastFieldsList" multiple="multiple">
                                <option value="None">None</option>
                                <c:forEach items="${fieldList}" var="field">
                                    <option value="${field.name}">${field.name}</option>
                                </c:forEach>
                                <option value="Other">Other - Explain in Q12</option>
                            </select>
                            <div class="detail-1-1">If you select"Other", please explain in the answer to question 12</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12" id="obtainedCertifications">
                            <div class="body-bold-1-1">5. What certifications, licenses or credentials have you obtained in the past? *</div>
                            <input type="hidden" name="obtainedCertification" value="${user.obtainedCertification}" form="updateForm">
                            <select data-placeholder="Start typing" class="multiple-select" id="pastCertificationsList" multiple="multiple">
                                <option value="None">None</option>
                                <c:forEach items="${certificationList}" var="certification">
                                    <option value="${certification.certification}">${certification.certification}</option>
                                </c:forEach>
                                <option value="Other">Other - Explain in Q12</option>
                            </select>
                            <div class="detail-1-1">If you select"Other", please explain in the answer to question 12</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12" id="pastEducations">
                            <div class="body-bold-1-1">6. What type of education have you completed? *</div>
                            <input type="hidden" name="pastEducation" value="${user.pastEducation}" form="updateForm">
                            <select data-placeholder="Start typing" class="multiple-select" id="pastEducationList" multiple="multiple">
                                <option value="Some high school; no diploma">Some high school; no diploma</option>
                                <option value="High school diploma or GED">High school diploma or GED</option>
                                <option value="Some trade/technical/vocational training; no certificate">Some trade/technical/vocational training; no certificate</option>
                                <option value="Completed trade/technical/vocational certificate or diploma">Completed trade/technical/vocational certificate or diploma</option>
                                <option value="Some college credit; no degree">Some college credit; no degree</option>
                                <option value="Associates degree">Associates degree</option>
                                <option value="Bachelors degree or more">Bachelors degree or more</option>
                            </select>
                            <div class="detail-1-1">Select all that apply</div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="body-bold-1-1">7. How much relevant work experience do you have? </div>
                            <div class="radio">
                                <label>
                                    <c:choose>
                                        <c:when test="${user.workExperience eq 'No relevant work experience'}">
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
                                        <c:when test="${user.workExperience eq 'Less than 1 year of work experience'}">
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
                                        <c:when test="${user.workExperience eq '1-3 years of work experience'}">
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
                                        <c:when test="${user.workExperience eq '3-5 years of work experience'}">
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
                                        <c:when test="${user.workExperience eq 'More than 5 years of work experience'}">
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
                                        <c:when test="${user.veteran eq 'Yes'}">
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
                                        <c:when test="${user.veteran eq 'No'}">
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
                                        <c:when test="${user.employed eq 'Yes'}">
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
                                        <c:when test="${user.employed eq 'No'}">
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
                                        <c:when test="${user.relocate eq 'Yes'}">
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
                                        <c:when test="${user.relocate eq 'No'}">
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
                            <div class="body-bold-1-1">11. Do you have a valid driver's license?</div>
                            <div class="radio-inline">
                                <label class="body-1-1">
                                    <c:choose>
                                        <c:when test="${user.validDriversLicense eq 'Yes'}">
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
                                        <c:when test="${user.validDriversLicense eq 'No'}">
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

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="body-bold-1-1">12. What more should employers know about your work history and employment goals?</div>
                            <textarea name="additionalInformation" id="" rows="3" class="input-clean body-1-1">${user.additionalInformation}</textarea>
                            <div class="detail-1-1">List additional certificates, skills, revelant work experience and accomplishments. Write what you are looking for as a career field, how far are you willing to travel, etc.</div>
                        </div>
                    </div>

                    <!-- Submit button -->
                    <div class="row text-center" style="border:none;">
                        <div class="col-md-6 candidate-profile-submit-button text-center hidden" id="submit-button">
                            <button type="submit" class="btn btn-link" form="updateForm" onclick="candidateUpdate();">Submit My Profile</button>
                        </div>
                        <p class="hidden body-bold-1-1" id="incompleteError" style="color: red; margin-top: 10px;">Please fill out the highlighted sections</p>
                    </div>
                </form>
            </div>