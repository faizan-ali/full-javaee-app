package co.workamerica.api.rest.candidates.profile;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**
 * Created by Faizan on 7/21/2016.
 */
@Path("/candidates")
public class Profile {

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Candidates updateCandidate(@DefaultValue("") @FormParam("firstName") String firstName,
                                      @DefaultValue("") @FormParam("lastName") String lastName,
                                      @DefaultValue("") @FormParam("email") String email,
                                      @DefaultValue("") @FormParam("password") String password,
                                      @DefaultValue("") @FormParam("anticipatedCertifications") String anticipatedCertifications,
                                      @DefaultValue("") @FormParam("obtainedCertifications") String obtainedCertifications,
                                      @DefaultValue("") @FormParam("fields") String fields,
                                      @DefaultValue("") @FormParam("pastFields") String pastFields,
                                      @DefaultValue("") @FormParam("city") String city,
                                      @DefaultValue("") @FormParam("state") String state,
                                      @DefaultValue("") @FormParam("phone") String phone,
                                      @DefaultValue("") @FormParam("alternatePhone") String alternatePhone,
                                      @DefaultValue("") @FormParam("school") String school,
                                      @DefaultValue("") @FormParam("completionDate") String completionDate,
                                      @DefaultValue("") @FormParam("workExperience") String workExperience,
                                      @DefaultValue("") @FormParam("veteran") String veteran,
                                      @DefaultValue("") @FormParam("employed") String employed,
                                      @DefaultValue("") @FormParam("relocate") String relocate,
                                      @DefaultValue("") @FormParam("additionalInformation") String additionalInformation,
                                      @DefaultValue("") @FormParam("pastEducation") String pastEducation,
                                      @DefaultValue("") @FormParam("validDriversLicense") String validDriversLicense,
                                      @DefaultValue("") @FormParam("zip") String zip,
                                      @DefaultValue("0") @FormParam("candidateID") String candidateID
    ) {

        HashMap<String, String> candidateDetails = new HashMap<>();
        Candidates candidate = CandidatePersistence.getCandidateByID(candidateID);

        if (candidate != null) {
            candidateDetails.put("firstName", firstName);
            candidateDetails.put("lastName", lastName);
            candidateDetails.put("city", city);
            candidateDetails.put("state", state);
            candidateDetails.put("email", email);
            candidateDetails.put("phone", phone);
            candidateDetails.put("alternatePhone", alternatePhone);
            candidateDetails.put("school", school);
            candidateDetails.put("completionDate", completionDate);
            candidateDetails.put("workExperience", workExperience);
            candidateDetails.put("veteran", veteran);
            candidateDetails.put("employed", employed);
            candidateDetails.put("relocate", relocate);
            candidateDetails.put("additionalInformation", additionalInformation);
            candidateDetails.put("pastEducation", pastEducation);
            candidateDetails.put("validDriversLicense", validDriversLicense);
            candidateDetails.put("zip", zip);
            candidateDetails.put("fields", fields);
            candidateDetails.put("anticipatedCertifications", anticipatedCertifications);
            candidateDetails.put("pastFields", pastFields);
            candidateDetails.put("obtainedCertifications", obtainedCertifications);
            candidateDetails.put("password", password);

            return CandidatePersistence.updateCandidate(candidateDetails, candidate);
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Candidates getCandidate(@PathParam("id") String candidateID) {
        return CandidatePersistence.getCandidateByID(candidateID);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Candidates createCandidate(@DefaultValue("") @FormParam("firstName") String firstName,
                                      @DefaultValue("") @FormParam("lastName") String lastName,
                                      @DefaultValue("") @FormParam("email") String email,
                                      @DefaultValue("") @FormParam("password") String password,
                                      @DefaultValue("") @FormParam("anticipatedCertifications") String anticipatedCertifications,
                                      @DefaultValue("") @FormParam("obtainedCertifications") String obtainedCertifications,
                                      @DefaultValue("") @FormParam("fields") String fields,
                                      @DefaultValue("") @FormParam("pastFields") String pastFields,
                                      @DefaultValue("") @FormParam("city") String city,
                                      @DefaultValue("") @FormParam("state") String state,
                                      @DefaultValue("") @FormParam("phone") String phone,
                                      @DefaultValue("") @FormParam("alternatePhone") String alternatePhone,
                                      @DefaultValue("") @FormParam("school") String school,
                                      @DefaultValue("") @FormParam("completionDate") String completionDate,
                                      @DefaultValue("") @FormParam("workExperience") String workExperience,
                                      @DefaultValue("") @FormParam("veteran") String veteran,
                                      @DefaultValue("") @FormParam("employed") String employed,
                                      @DefaultValue("") @FormParam("relocate") String relocate,
                                      @DefaultValue("") @FormParam("additionalInformation") String additionalInformation,
                                      @DefaultValue("") @FormParam("pastEducation") String pastEducation,
                                      @DefaultValue("") @FormParam("validDriversLicense") String validDriversLicense,
                                      @DefaultValue("") @FormParam("zip") String zip,
                                      @DefaultValue("0") @FormParam("candidateID") String candidateID
    ) {

        HashMap<String, String> candidateDetails = new HashMap<>();

        candidateDetails.put("firstName", firstName);
        candidateDetails.put("lastName", lastName);
        candidateDetails.put("city", city);
        candidateDetails.put("state", state);
        candidateDetails.put("email", email);
        candidateDetails.put("phone", phone);
        candidateDetails.put("alternatePhone", alternatePhone);
        candidateDetails.put("school", school);
        candidateDetails.put("completionDate", completionDate);
        candidateDetails.put("workExperience", workExperience);
        candidateDetails.put("veteran", veteran);
        candidateDetails.put("employed", employed);
        candidateDetails.put("relocate", relocate);
        candidateDetails.put("additionalInformation", additionalInformation);
        candidateDetails.put("pastEducation", pastEducation);
        candidateDetails.put("validDriversLicense", validDriversLicense);
        candidateDetails.put("zip", zip);
        candidateDetails.put("fields", fields);
        candidateDetails.put("anticipatedCertifications", anticipatedCertifications);
        candidateDetails.put("pastFields", pastFields);
        candidateDetails.put("obtainedCertifications", obtainedCertifications);
        candidateDetails.put("password", password);

        return CandidatePersistence.updateCandidate(candidateDetails, null);
    }
}
