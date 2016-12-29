package co.workamerica.api.campaigns;

import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.SchoolDataAccessObject;
import co.workamerica.functionality.sendgrid.API.SendGridObject;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Path("/add/amca")
public class AddCandidateAMCA {

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
    public String newCandidatePOST(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName,
                                    @FormParam("email") String email, @FormParam("source") String source,
                                    @FormParam("campaign") String campaign, @FormParam("field") String field,
                                    @FormParam("certification") String certification, @FormParam("browser") String browser,
                                    @FormParam("browserVersion") String browserVersion, @FormParam("os") String os,
                                    @FormParam("osVersion") String osVersion, @FormParam("device") String device,
                                    @FormParam("deviceType") String deviceType, @FormParam("deviceVendor") String deviceVendor) {

        firstName = firstName == null || firstName.equals("undefined") ? "" : firstName;
        lastName = lastName == null || lastName.equals("lastName") ? "" : lastName;
        email = email == null ? "" : email;
        source = source == null ? "" : source;
        campaign = campaign == null ? "" : campaign;
        field = field == null ? "" : field;
        certification = certification == null || certification.equals("undefined") ? "" : certification;

        HashMap<String, String> map = new HashMap<>();

        if (!CandidatePersistence.checkIfExistsByEmail(email)) {

            map.put("firstName", firstName);
            map.put("lastName", lastName);
            map.put("email", email);
            map.put("fields", field);
            map.put("anticipatedCertifications", certification);
            map.put("authorized", "Yes");
            map.put("workAmericaCreated", "No");

            map.put("accountOriginSourceID", SchoolDataAccessObject.getSchoolByName(campaign).getSchoolID() + "");
            map.put("accountOriginSource", campaign);
            map.put("accountOriginBrowser", browser);
            map.put("accountOriginBrowserVersion", browserVersion);
            map.put("accountOriginOS", os);
            map.put("accountOriginOSVersion", osVersion);
            map.put("accountOriginDevice", device);
            map.put("accountOriginDeviceType", deviceType);
            map.put("accountOriginDeviceVendor", deviceVendor);

            if (campaign.equalsIgnoreCase("comptia") || campaign.equalsIgnoreCase("AMCA")) {
                map.put("accountOriginMarketingCampaign", "CredentialingInstitute");
            } else if (campaign.equalsIgnoreCase("facebook")) {
                map.put("accountOriginMarketingCampaign", "SocialMedia");
            }

            try {
                CandidatePersistence.createCandidate(map);
                String password = CandidatePersistence.newPasswordGenerator(map);

                // SendGrid welcome e-mail
                try {
                    boolean sent = false;
                    sent = SendGridObject.sendEmail(email, password, firstName, "Register");
                    if (!sent) {
                        SendGridObject.sendEmail(email, password, firstName, "Error");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "{message: success";
    }
}
