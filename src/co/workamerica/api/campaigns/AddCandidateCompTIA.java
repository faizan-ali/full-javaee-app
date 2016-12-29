package co.workamerica.api.campaigns;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.sendgrid.API.SendGridContacts;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
import co.workamerica.functionality.shared.utilities.Clock;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Path("/add/comptia")
public class AddCandidateCompTIA {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String getTest () {
        return "All good";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String newCandidatePOST(@FormParam("zip") String zip, @FormParam("phone") String phone,
                                   @FormParam("email") String email, @FormParam("source") String campaign,
                                   @FormParam("certification") String certification, @FormParam("browser") String browser,
                                   @FormParam("browserVersion") String browserVersion, @FormParam("os") String os,
                                   @FormParam("osVersion") String osVersion, @FormParam("device") String device,
                                   @FormParam("deviceType") String deviceType, @FormParam("deviceVendor") String deviceVendor,
                                   @FormParam ("employers") String employers, @FormParam("firstName") String firstName,
                                   @FormParam("lastName") String lastName) {

        String field = "IT";

        firstName = firstName == null ? "" : firstName;
        lastName = lastName == null ? "" : lastName;
        phone = phone == null || phone.equalsIgnoreCase("undefined") ? "" : phone;
        zip = zip == null || zip.equalsIgnoreCase("undefined") ? "" : zip;
        email = email == null || email.equalsIgnoreCase("undefined") ? "" : email;
        campaign = campaign == null || campaign.equalsIgnoreCase("undefined") ? "" : campaign;
        certification = certification == null || certification.equals("undefined") ? "" : certification;

        HashMap<String, String> map = new HashMap<>();
        if (!CandidatePersistence.checkIfExistsByEmail(email)) {

            map.put("email", email);
            map.put("fields", field);
            map.put("workAmericaCreated", "No");
            map.put("authorized", "Yes");
            map.put("firstName", firstName);
            map.put("lastName", lastName);

            if (employers != null && !employers.isEmpty() && !employers.contains("SELECT")) {
                map.put("additionalInformation", "Interested in companies like " + employers);
            }


            map.put("accountOriginSourceID", 161 + "");
            map.put("accountOriginSource", "CompTIA");
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

                // Adding to SendGrid
                try {
                    SendGridContacts.add(email, firstName, lastName, zip, Clock.getCurrentDate(), "", "No", Clock.getCurrentDate(), "CompTIA", "CredentialingInstitute", candidate.getCandidateID(), "IT", "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // SendGrid welcome e-mail
                try {
                    boolean sent = false;
                    sent = SendGridObject.sendEmail(email, password, "", "Campaign");
                    if (!sent) {
                        SendGridObject.sendEmail(email, password, "", "Error");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "{message: success}";
        } else {
            map.put("authorized", "Yes");
            map.put("email", email);
            map.put("fields", field);
            map.put("workAmericaCreated", "No");
            map.put("anticipatedCertifications", certification);
            map.put("zip", zip);
            map.put("phone", phone);
            map.put("accountOriginSourceID", 161 + "");
            map.put("accountOriginSource", campaign);
            map.put("accountOriginBrowser", browser);
            map.put("accountOriginBrowserVersion", browserVersion);
            map.put("accountOriginOS", os);
            map.put("accountOriginOSVersion", osVersion);
            map.put("accountOriginDevice", device);
            map.put("accountOriginDeviceType", deviceType);
            map.put("accountOriginDeviceVendor", deviceVendor);
            map.put("accountOriginMarketingCampaign", "CredentialingInstitute");

            if (employers != null && !employers.isEmpty() && !employers.contains("SELECT")) {
                map.put("additionalInformation", "Interested in companies like " + employers);
            }

            CandidatePersistence.updateCandidate(map, CandidatePersistence.getCandidateByEmail(email));
            return "{message: success}";
        }
    }
}
