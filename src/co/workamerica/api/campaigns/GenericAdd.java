package co.workamerica.api.campaigns;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.SchoolDataAccessObject;
import co.workamerica.functionality.sendgrid.API.SendGridContacts;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
import co.workamerica.functionality.shared.utilities.Clock;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Path("/campaign/add")
public class GenericAdd {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String get() {
        return "{message:Invalid HTTP method}";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String newCandidatePOST(@FormParam("zip") String zip, @FormParam("phone") String phone,
                                   @FormParam("email") String email, @FormParam("source") String source,
                                   @FormParam("certification") String certification, @FormParam("browser") String browser,
                                   @FormParam("browserVersion") String browserVersion, @FormParam("os") String os,
                                   @FormParam("osVersion") String osVersion, @FormParam("device") String device,
                                   @FormParam("deviceType") String deviceType, @FormParam("deviceVendor") String deviceVendor,
                                   @FormParam("employers") String employers, @FormParam("firstName") String firstName,
                                   @FormParam("lastName") String lastName, @FormParam("field") String field) {

        firstName = firstName == null ? "" : firstName;
        lastName = lastName == null ? "" : lastName;
        field = field == null ? "" : field;
        employers = employers == null ? "" : employers;
        phone = phone == null ? "" : phone;
        zip = zip == null ? "" : zip;
        email = email == null ? "" : email;
        source = source == null ? "" : source;
        certification = certification == null ? "" : certification;

        HashMap<String, String> map = new HashMap<>();
        int sourceID = SchoolDataAccessObject.getSchoolIDByName(source);

        if (!CandidatePersistence.checkIfExistsByEmail(email)) {

            map.put("approved", "No");
            map.put("authorized", "Yes");
            map.put("email", email);
            map.put("fields", field);
            map.put("workAmericaCreated", "No");
            map.put("firstName", firstName);
            map.put("lastName", lastName);
            map.put("field", field);
            map.put("employers", employers);
            map.put("phone", phone);
            map.put("zip", zip);
            map.put("anticipatedCertifications", certification);
            map.put("workAmericaCreated", "No");

            if (employers != null && !employers.isEmpty() && !employers.contains("SELECT")) {
                map.put("additionalInformation", "Interested in companies like " + employers);
            }

            map.put("accountOriginSourceID", sourceID + "");
            map.put("accountOriginSource", source);
            map.put("accountOriginBrowser", browser);
            map.put("accountOriginBrowserVersion", browserVersion);
            map.put("accountOriginOS", os);
            map.put("accountOriginOSVersion", osVersion);
            map.put("accountOriginDevice", device);
            map.put("accountOriginDeviceType", deviceType);
            map.put("accountOriginDeviceVendor", deviceVendor);
            map.put("accountOriginMarketingCampaign", "CredentialingInstitute");

            try {
                Candidates candidate = CandidatePersistence.createCandidate(map);
                String password = CandidatePersistence.newPasswordGenerator(map);

                SendGridContacts.add(email, firstName, lastName, zip, Clock.getCurrentDate(), "", "No", Clock.getCurrentDate(), source, "CredentialingInstitute", candidate.getCandidateID(), field, "");
                SendGridObject.sendEmail(email, password, "", "Campaign");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "{message: success}";
        } else {
            Candidates candidate = CandidatePersistence.getCandidateByEmail(email);

            map.put("fields", field);
            map.put("firstName", firstName);
            map.put("lastName", lastName);
            map.put("field", field);
            map.put("employers", employers);
            map.put("phone", phone);
            map.put("zip", zip);
            map.put("email", email);
            map.put("anticipatedCertifications", certification);

            if (employers != null && !employers.isEmpty() && !employers.contains("SELECT")) {
                map.put("additionalInformation", "Interested in companies like " + employers);
            }

            CandidatePersistence.updateCandidate(map, candidate);
            return "{message: success}";
        }
    }
}
