<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

            <!-- Container for profile summary card (view) -->
            <div class="col-md-6 col-xs-12 box-shadow hidden-xs" id="profile-card-view">

                <!-- Container for profile picture and resume download button -->
                <div class="col-md-3 candidate-photo-resume-box text-center remove-horizontal-padding">
                    <c:choose>
                        <c:when test="${empty user.photo}">
                            <img src="img/generic-profile-add.png" alt="Profile Picture">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/images/${user.candidateID}" alt="Profile Picture">
                        </c:otherwise>
                    </c:choose>

                    <p class="small body-bold-1-1" style="color: green">${resumeSuccess}</p>
                    <c:choose>
                        <c:when test="${not empty user.resume}">
                            <form action="ResumeRetrieverServlet">
                                <input type="hidden" name="candidateID" value="${user.candidateID }">
                                <input type="hidden" name="source" value="candidate" />
                                <button type="submit" class="btn candidate-resume-button-container" style="width: 100%"><span class="icon-doc" style="font-size: 15px; color: #FFFFFF;"></span><span class="field-title-1-5">View Resume</span></button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn candidate-no-resume-button" style="width: 100%;">
                                <div class="field-title-1-1">No Resume</div>
                            </button>
                        </c:otherwise>
                    </c:choose>
                    <input type="hidden" value="${resume}" id="hiddenResume">
                </div>

                <!-- Container for profile summary within first profile card -->
                <div class="col-md-7 candidate-summary-box remove-horizontal-padding">

                    <!-- Container for name and location -->
                    <div class="candidate-title-box">
                        <div class="heading-1-1">${user.firstName} ${user.lastName}</div>
                        <div class="body-1-1">
                            <c:if test="${not empty user.city}">
                                ${user.city},
                            </c:if>
                            ${user.state} ${user.zip}
                        </div>
                    </div>

                    <!-- Container for labels within first profile card -->
                    <div class="candidate-summary-label-box">
                        <div class="row">
                            <div class="col-xs-5 field-title-1-1 candidate-summary-label remove-horizontal padding">Email</div>
                            <div class="col-xs-6 body-1-1 remove-horizontal-padding">${user.email}</div>
                        </div>
                        <div class="row">
                            <div class="col-xs-5 field-title-1-1 candidate-summary-label remove-horizontal padding">Phone</div>
                            <div class="col-xs-6 body-1-1 remove-horizontal-padding">${user.phone}</div>
                        </div>
                        <div class="row">
                            <div class="col-xs-5 field-title-1-1 candidate-summary-label remove-horizontal padding">Alt Phone</div>
                            <div class="col-xs-6 body-1-1 remove-horizontal-padding">${user.alternatePhone}</div>
                        </div>
                        <div class="row" style="margin-top: 25px;">
                            <div class="col-xs-5 field-title-1-1 candidate-summary-label remove-horizontal padding">Occupational Field</div>

                            <div class="col-xs-6 body-1-1 remove-horizontal-padding">
                                <div>${user.field}</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-5 field-title-1-1 candidate-summary-label remove-horizontal padding">Current School</div>
                            <div class="col-xs-6 body-1-1 remove-horizontal-padding">
                                <c:choose>
                                    <c:when test="${empty user.school}">
                                        None
                                    </c:when>
                                    <c:otherwise>
                                        ${user.school}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Container for candidate details (view) -->
            <div class="col-md-6 col-xs-12 candidate-detail-container hidden-xs" id="candidate-detail-view">

                <div class="row">
                    <div class="col-xs-6">
                        <div class="field-title-1-1">Certifications</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${empty user.anticipatedCertification}">
                                    None
                                </c:when>
                                <c:otherwise>
                                    ${user.anticipatedCertification}
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-xs-6">
                        <div class="field-title-1-1">Completion Date</div>
                        <div class="body-1-1">
                            <c:if test="${not empty user.completionDate}">
                                ${user.completionDate}
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Past Training</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${not empty user.pastField}">
                                    ${user.pastField}
                                </c:when>
                                <c:otherwise>
                                    Unspecified
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Previously Obtained Certifications</div>
                        <div class="body-1-1">${user.obtainedCertification}</div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Education Level</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${not empty user.pastEducation}">
                                    ${user.pastEducation}
                                </c:when>
                                <c:otherwise>
                                    Unspecified
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Work Experience</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${not empty user.workExperience}">
                                    ${user.workExperience}
                                </c:when>
                                <c:otherwise>
                                    Unspecified
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Veteran</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${not empty user.veteran}">
                                    ${user.veteran}
                                </c:when>
                                <c:otherwise>
                                    Unspecified
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Currently Employed</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${not empty user.employed}">
                                    ${user.employed}
                                </c:when>
                                <c:otherwise>
                                    Unspecified
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Willing to Relocate/Move</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${not empty user.relocate}">
                                    ${user.relocate}
                                </c:when>
                                <c:otherwise>
                                    Unspecified
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Valid Driver's License</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${not empty user.validDriversLicense}">
                                    ${user.validDriversLicense}
                                </c:when>
                                <c:otherwise>
                                    Unspecified
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Additional Information (Employment History and/or Goals)</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${not empty user.additionalInformation}">
                                    ${user.additionalInformation}
                                </c:when>
                                <c:otherwise>
                                    None
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="field-title-1-1">Allow Employers to Contact Me</div>
                        <div class="body-1-1">
                            <c:choose>
                                <c:when test="${user.authorized eq 'Yes'}">
                                    Yes
                                </c:when>
                                <c:when test="${user.authorized eq 'No'}">
                                    No
                                </c:when>
                                <c:otherwise>
                                    Unspecified
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>