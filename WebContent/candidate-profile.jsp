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
            <title>${candidate.firstName} ${candidate.lastName }</title>

            <c:import url="/header.jsp" />

            <!-- Page specific CSS -->
            <link href="css/candidate-profile.css" rel="stylesheet">

            <div class="container-fluid platform-nav-container">
                <div class="row row-centered platform-nav">

                    <!-- Desktop nav, hidden from mobile -->
                    <div class="hidden-xs hidden-sm">
                        <div class="col-md-4 platform-nav-back">
                            <div class="icon-arrow-left-circle col-md-1" style="font-size: 20px; color:white;"></div>
                            <div class="subhead-1-3 col-md-9">
                                <c:choose>
                                    <c:when test="${backToPipeline != null}">
                                        <a href="pipeline.jsp" style = "color:white !important">Back to Candidate Pipeline</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="results.jsp" style = "color:white !important">Back To Search Results</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-md-2 text-center platform-nav-button subhead-1-2 pull-right" style = "color:white !important"><a href="pipeline.jsp">Candidate Pipeline</a></div>
                        <div class="col-md-2 text-center platform-nav-button subhead-1-2 pull-right" style = "color:white !important"><a href="landing.jsp">New Search</a></div>
                    </div>

                    <!-- Mobile Nav -->
                    <div class="visible-xs visible-sm">
                        <div class="col-xs-12 text-center" style="margin-top: 8px;">
                            <div class="icon-arrow-left-circle col-xs-2 col-centered" style="font-size: 16px; color:white;"></div>
                            <div class="subhead-1-3 col-xs-10 col-sm-5 col-centered remove-horizontal-padding-mobile" style="font-size: 12px !important; color:white !important;">
                                <c:choose>
                                    <c:when test="${backToPipeline != null}">
                                        <a href="pipeline.jsp">Back to Candidate Pipeline</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="results.jsp">Back To Search Results</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="col-xs-6 platform-nav-button subhead-1-2" style = "color:white !important"><a href="landing.jsp">New Search</a></div>
                            <div class="col-xs-6 platform-nav-button subhead-1-2" style = "color:white !important">Candidate Pipeline</div>
                        </div>
                    </div>

                </div>
            </div>

            <!-- Container for entire candidate profile -->
            <div id="candidate-background-container">

                <div class="container" id="profile-container">

                    <div class="row heading-1-1" id="candidate-page-title">Candidate Profile</div>

                    <!-- Container for first profile card and pipeline box -->
                    <div class="row" id="candidate-page-first-row">

                        <div class="col-md-7 col-sm-7 box-shadow" id="" style="margin-right:22px;">

                            <!-- Container for profile picture and resume download button -->
                            <div class="col-md-3 candidate-photo-resume-box text-center remove-horizontal-padding">
                                <c:choose>
                                    <c:when test="${empty candidate.photo}">
                                        <img src="img/generic-profile.png" alt="Profile Picture">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/images/${candidate.candidateID}" class="img-responsive center-block" alt="Profile Picture">
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty candidate.resume}">
                                        <form action="ResumeRetrieverServlet">
                                            <input type="hidden" name="candidateID" value="${candidate.candidateID }">
                                            <input type="hidden" name="source" value="client" />
                                            <button type="submit" class="btn candidate-resume-button center-block" style="width:auto;"><span class="icon-doc" style="font-size: 15px; color: #FFFFFF;"></span><span class="field-title-1-5">View Resume</span></button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn candidate-no-resume-button" style="width: 100%;">
                                            <div class="field-title-1-1">No Resume</div>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <!-- Container for profile summary within first profile card -->
                            <div class="col-md-7 candidate-summary-box remove-horizontal-padding">

                                <!-- Container for name and location -->
                                <div class="candidate-title-box">
                                    <div class="heading-1-1">${candidate.firstName} ${candidate.lastName}</div>
                                    <div class="body-1-1">
                                        <c:if test="${not empty candidate.city}">
                                            ${candidate.city},
                                        </c:if>
                                        ${candidate.state}</div>
                                </div>

                                <!-- Container for labels within first profile card -->
                                <div class="candidate-summary-label-box">
                                    <div class="row">
                                        <div class="col-md-2 col-xs-3 field-title-1-1 candidate-summary-label remove-horizontal padding">Email</div>
                                        <div class="col-md-10 col-xs-9 body-1-1 remove-horizontal-padding">${candidate.email}</div>
                                        <div class="col-md-2 col-xs-3 field-title-1-1 candidate-summary-label remove-horizontal padding">Phone</div>
                                        <div class="col-md-10 col-xs-8 body-1-1 remove-horizontal-padding">${candidate.phone}</div>
                                        <c:if test="${not empty candidate.alternatePhone}">
                                            <div class="col-xs-3 field-title-1-1 candidate-summary-label remove-horizontal padding">Alt Phone</div>
                                        </c:if>
                                        <div class="col-xs-8 body-1-1 remove-horizontal-padding">${candidate.alternatePhone}</div>
                                    </div>

                                    <div class="row" style="margin-top: 25px;">
                                        <div class="col-md-4 col-xs-3 field-title-1-1 candidate-summary-label remove-horizontal padding">Field</div>

                                        <div class="col-xs-8 body-1-1 remove-horizontal-padding">
                                            <c:choose>
                                                <c:when test="${empty candidate.field}">
                                                    None
                                                </c:when>
                                                <c:otherwise>
                                                    ${candidate.field}
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <div class="col-md-4 col-xs-3 field-title-1-1 candidate-summary-label remove-horizontal padding">School</div>
                                        <div class="col-md-8 col-xs-7 body-1-1 remove-horizontal-padding">
                                            <c:choose>
                                                <c:when test="${empty candidate.school}">
                                                    None
                                                </c:when>
                                                <c:otherwise>
                                                    ${candidate.school}
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>



                                </div>
                            </div>
                        </div>

                        <!-- Container for Pipeline box (if candidate does not exists in Pipeline or needs to be edited -->
                        <div class="col-md-4 col-sm-4 col-xs-12 box-shadow pipeline-box" id="add">

                            <div class="pipeline-box-title subhead-1-1">ADD TO PIPELINE</div>
                            <div class="pipeline-box-update-title subhead-1-1 hidden">Update Watchlist Status</div>

                            <form action="PipelineServlet">
                                <div class="pipeline-controls-container">
                                    <div class="col-md-6 status-select">
                                        <div class="body-bold-1-1">Status</div>
                                        <select name="status" class="form-control body-1-1">
                                            <option value="${candidatePipelineEntity.status}">${candidatePipelineEntity.status}</option>
                                            <option value="Hired">Hired</option>
                                            <option value="Offer Extended">Offer Extended</option>
                                            <option value="Scheduled for Interview">Scheduled for Interview
                                            </option>
                                            <option value="Under Review">Under Review</option>
                                            <option value="Not Interested">Not Interested</option>
                                            <option value="Declined">Declined</option>
                                        </select>
                                    </div>

                                    <div class="col-md-6 rating-container rating-gly-star">
                                        <div class="body-bold-1-1">Rating</div>
                                        <input name="rating" type="text" class="rate" value="${candidatePipelineEntity.rating}">
                                    </div>
                                </div>
                                <div class="col-md-12 text-center pipeline-box-submit">
                                    <input type="hidden" name="candidateID" value="${candidate.candidateID}">
                                    <input type="hidden" name="pipelineID" value="${candidatePipelineEntity.clientPipelineID}">
                                    <button type="submit" class="btn btn-link pipeline-submit">Submit Status</button>
                                </div>
                            </form>
                        </div>

                        <!-- Container for pipeline detail display box. Visible if candidate exists in pipeline -->
                        <div class="col-md-4 col-sm-4 col-xs-12 box-shadow pipeline-box" id="show">
                            <div class="candidate-title-box">
                                <div class="pipeline-box-title subhead-1-1">WATCHLIST DETAILS</div>
                            </div>

                            <div class="pipeline-details-container">
                                <div class="row pipeline-details-box" style="margin-top: 18px;">
                                    <div class="col-xs-4 body-bold-1-1">Status</div>
                                    <div class="col-xs-8 body-1-1">
                                        ${candidatePipelineEntity.status}
                                    </div>
                                </div>
                                <div class="row pipeline-details-box">
                                    <div class="col-xs-4 body-bold-1-1">Rating</div>
                                    <div class="col-xs-8 rating-container rating-gly-star">
                                        <input name="rating" type="text" class="rate" value="${candidatePipelineEntity.rating}">
                                    </div>
                                </div>
                                <div class="row pipeline-details-box">
                                    <div class="col-xs-4 body-bold-1-1">Date Added</div>
                                    <div class="col-xs-8 body-1-1">${candidatePipelineEntity.dateAdded}</div>
                                </div>


                                <div class="col-md-12 text-center pipeline-box-edit">
                                    <button id="editPipeline" onClick="showUpdateBox();" type="submit" class="btn btn-link " style="width: 100%; color: white;">Edit Watchlist Status</button>
                                </div>

                            </div>
                        </div>

                        <div class="col-md-4 col-sm-4 col-xs-12 box-shadow pipeline-box" id="update">

                            <div class="pipeline-box-title subhead-1-1">UPDATE WATCHLIST STATUS</div>

                            <form action="PipelineEditServlet">
                                <div class="pipeline-controls-container">
                                    <div class="col-md-6 status-select">
                                        <div class="body-bold-1-1">Status</div>
                                        <select name="status" class="form-control body-1-1">
                                            <option value="${candidatePipelineEntity.status}">${candidatePipelineEntity.status}</option>
                                            <option value="Hired">Hired</option>
                                            <option value="Offer Extended">Offer Extended</option>
                                            <option value="Scheduled for Interview">Scheduled for Interview
                                            </option>
                                            <option value="Under Review">Under Review</option>
                                            <option value="Not Interested">Not Interested</option>
                                            <option value="Declined">Declined</option>
                                        </select>
                                    </div>

                                    <div class="col-md-6 rating-container rating-gly-star">
                                        <div class="body-bold-1-1">Rating</div>
                                        <input name="rating" type="text" class="rate" value="${candidatePipelineEntity.rating}">
                                    </div>
                                </div>
                                <div class="col-md-12 text-center pipeline-box-submit">
                                    <input type="hidden" name="candidateID" value="${candidate.candidateID}">
                                    <input type="hidden" name="pipelineID" value="${candidatePipelineEntity.clientPipelineID}">
                                    <button type="submit" class="btn btn-link">Submit Status</button>
                                </div>
                            </form>
                        </div>

                    </div>

                    <!-- Candidate detail container -->
                    <div class="row" style="margin-bottom: 50px;">
                        <div class="col-md-7 col-sm-7 candidate-detail-container">

                            <div class="row">
                                <div class="col-xs-6">
                                    <div class="field-title-1-1">Certifications</div>
                                    <div class="body-1-1">
                                        <c:choose>
                                            <c:when test="${empty candidate.anticipatedCertification}">
                                                None
                                            </c:when>
                                            <c:otherwise>
                                                ${candidate.anticipatedCertification}
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="field-title-1-1">CompletionDate</div>
                                    <div class="body-1-1">
                                        <c:if test="${not empty candidate.completionDate}">
                                            ${candidate.completionDate}
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <c:if test="${not empty candidate.pastField}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="field-title-1-1">Past Training</div>
                                        <div class="body-1-1">
                                            <c:choose>
                                                <c:when test="${not empty candidate.pastField}">
                                                    ${candidate.pastField}
                                                </c:when>
                                                <c:otherwise>
                                                    Unspecified
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty candidate.obtainedCertification}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="field-title-1-1">Previously Obtained Certifications</div>
                                        <div class="body-1-1">${candidate.obtainedCertification}</div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty candidate.pastEducation}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="field-title-1-1">Education Level</div>
                                        <div class="body-1-1">
                                            <c:choose>
                                                <c:when test="${not empty candidate.pastEducation}">
                                                    ${candidate.pastEducation}
                                                </c:when>
                                                <c:otherwise>
                                                    Unspecified
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty candidate.workExperience}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="field-title-1-1">Work Experience</div>
                                        <div class="body-1-1">
                                            <c:choose>
                                                <c:when test="${not empty candidate.workExperience}">
                                                    ${candidate.workExperience} years
                                                </c:when>
                                                <c:otherwise>
                                                    Unspecified
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty candidate.veteran}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="field-title-1-1">Veteran</div>
                                        <div class="body-1-1">
                                            <c:choose>
                                                <c:when test="${not empty candidate.veteran}">
                                                    ${candidate.veteran}
                                                </c:when>
                                                <c:otherwise>
                                                    Unspecified
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty candidate.employed}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="field-title-1-1">Currently Employed</div>
                                        <div class="body-1-1">
                                            <c:choose>
                                                <c:when test="${not empty candidate.employed}">
                                                    ${candidate.employed}
                                                </c:when>
                                                <c:otherwise>
                                                    Unspecified
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty candidate.workExperience}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="field-title-1-1">Willing to Relocate/Move</div>
                                        <div class="body-1-1">
                                            <c:choose>
                                                <c:when test="${not empty candidate.relocate}">
                                                    ${candidate.relocate}
                                                </c:when>
                                                <c:otherwise>
                                                    Unspecified
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty candidate.validDriversLicense}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="field-title-1-1">Valid Driver's License</div>
                                        <div class="body-1-1">
                                            <c:choose>
                                                <c:when test="${not empty candidate.validDriversLicense}">
                                                    ${candidate.validDriversLicense}
                                                </c:when>
                                                <c:otherwise>
                                                    Unspecified
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty candidate.workExperience}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="field-title-1-1">Additional Information (Employment History and/or Goals)</div>
                                        <div class="body-1-1">
                                            <c:choose>
                                                <c:when test="${not empty candidate.additionalInformation}">
                                                    ${candidate.additionalInformation}
                                                </c:when>
                                                <c:otherwise>
                                                    None
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>

                        <div class="col-md-4 col-sm-4 col-xs-12 pipeline-notes remove-horizontal-padding remove-horizontal-padding-mobile" id="notes">
                            <div class="pipeline-box-title subhead-1-1">NOTES</div>
                            <div class="pipeline-notes-body body-1-1"></div>
                            <form action="NotesServlet" method="post">
                                <textarea class="form-control" rows="5" name="notes" placeholder="Enter Notes Here">${candidatePipelineEntity.notes}</textarea>

                                <div class="col-md-12 text-center pipeline-box-submit" style="margin-top: 0px; margin-bottom: 15px;">
                                    <input type="hidden" name="pipelineID" value="${candidatePipelineEntity.clientPipelineID}">
                                    <input type="hidden" name="candidateID" value="${candidate.candidateID}">
                                    <button type="submit" class="btn btn-link">Submit</button>
                                </div>
                            </form>
                        </div>

                        <div class="col-md-4 col-sm-4 col-xs-12 pipeline-notes remove-horizontal-padding remove-horizontal-padding-mobile">
                            <c:if test="${not empty candidate.phone}">
                                <c:if test="${not empty user.assignedNumber}">
                                    <form action="ClientTextServlet?candidateID=${candidate.candidateID}" method="post">
                                        <textarea class="form-control" rows="5" name="message" placeholder="Enter Message"></textarea>
                                        <button type="submit" class="btn btn-default btn-lg envelope center-block" data-toggle="modal" data-target="#text" style="width:100%">
                                            Message Candidate
                                        </button>
                                        <div class="body-bold-1-1 small" style="color: red;">${employerError}</div>
                                        <div class="body-bold-1-1 small" style="color: green;">${employerSuccess}</div>
                                    </form>
                                </c:if>
                            </c:if>
                        </div>

                    </div>
                </div>
            </div>



            <c:import url="/footer.html" />
            <c:remove var="employerError" />
            <c:remove var="employerSuccess" />
            
            <!-- Star rating JavaScript -->
            <script type="text/javascript" src="js/star-rating.js"></script>
            <script>
                jQuery(document).ready(function () {
                    $(".rate").rating({
                        min: 0,
                        max: 5,
                        step: 0.5,
                        size: 'xs',
                        showClear: false,
                        showCaption: false
                    });

                });
            </script>
            <!-- Script that shows the appropriate box depending on the candidate's existence in pipeline -->
            <script>
                var exists = '${existsInPipeline}';
                $('#update').addClass("hidden");
                if (exists === "true") {
                    $('#add').addClass("hidden");
                    $('#notes').removeClass("hidden");
                } else {
                    $('#show').addClass("hidden");
                    $('#notes').addClass("hidden");
                }
            </script>

            <script>
                function showUpdateBox() {
                    $('#show').addClass("hidden");
                    $('#add').addClass("hidden");
                    $('#update').removeClass("hidden");
                }
            </script>
            </body>

        </html>