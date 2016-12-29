package co.workamerica.functionality.sendgrid.API;

import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import com.sendgrid.Client;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Faizan on 7/22/2016.
 */
public class SendGridContacts {

    private static final String baseURI = "api.sendgrid.com";
    private static final String endpoint = "/v3/contactdb/recipients";
    private static final String apiKey = "";

    public SendGridContacts() {

    }

    public static JSONObject addJSON(String email, String firstName, String lastName, String zip, String dateCreated, String school, String workAmericaCreated,
                                        String dateLastUpdated, String profileSource, String marketingCampaign, int candidateID, String field, String pastField) {

        if (email != null && !email.isEmpty() && CustomUtilities.isValidEmail(email)) {

            firstName = firstName != null ? firstName.trim() : "";
            lastName = lastName != null ? lastName.trim() : "";
            zip = zip != null || !zip.isEmpty() ? zip.trim() : "0";
            dateCreated = dateCreated != null ? dateCreated.trim() : "07/04/2012";
            school = school != null ? school.trim() : "";
            workAmericaCreated = workAmericaCreated != null ? workAmericaCreated.trim() : "";
            dateLastUpdated = dateLastUpdated.isEmpty() ? dateCreated : dateLastUpdated;
            profileSource = profileSource != null ? profileSource.trim() : "";
            marketingCampaign = marketingCampaign != null ? marketingCampaign.trim() : "";
            field = field != null ? field.trim() : (pastField != null ? pastField.trim() : "");

            int zipInteger = 0;

            try {
                zipInteger = Integer.parseInt(zip);
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject contact = new JSONObject();
            contact.put("email", email);
            contact.put("first_name", firstName);
            contact.put("last_name", lastName);
            contact.put("zip", zipInteger);
            contact.put("dateCreated", dateCreated);
            contact.put("school", school);
            contact.put("workAmericaCreated", workAmericaCreated);
            contact.put("dateLastUpdated", dateLastUpdated);
            contact.put("profileSource", profileSource);
            contact.put("marketingCampaign", marketingCampaign);
            contact.put("candidateID", candidateID);
            contact.put("field", field);
            return contact;
        }

        return null;
    }


    public static boolean add(String email, String firstName, String lastName, String zip, String dateCreated, String school, String workAmericaCreated,
                               String dateLastUpdated, String profileSource, String marketingCampaign, int candidateID, String field, String pastField) {

        JSONObject contact = addJSON(email, firstName, lastName, zip, dateCreated, school, workAmericaCreated, dateLastUpdated, profileSource,
                marketingCampaign, candidateID, field, pastField);

        if (contact != null) {
            JSONArray array = new JSONArray();
            array.put(contact);

            return post(array);
        }

        return false;
    }

    public static boolean update(String email, String firstName, String lastName, String zip, String school, int candidateID, String field) {
        if (candidateID > 0 && email != null && CustomUtilities.isValidEmail(email)) {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("dateLastUpdated", Clock.getCurrentDate());

            if (firstName != null && !firstName.isEmpty()) {
                json.put("first_name", firstName);
            }

            if (lastName != null && !lastName.isEmpty()) {
                json.put("last_name", lastName);
            }

            if (zip != null && !zip.isEmpty()) {
                json.put("zip", zip);
            }

            if (school != null && !school.isEmpty()) {
                json.put("school", school);
            }

            if (field != null && !field.isEmpty()) {
                json.put("field", field);
            }
            return post(json);
        }
        return false;
    }

    public static boolean delete (int candidateID, String email) {
        if (candidateID > 0) {

        }
        return false;
    }

    public static boolean post (JSONObject json) {
        if (json != null) {
            JSONArray array = new JSONArray();
            array.put(json);
            return post(array);
        }
        return false;
    }

    public static boolean post(JSONArray array) {
        if (array != null) {
            Client client = new Client();
            Request request = new Request();
            Map<String, String> requestHeaders = new HashMap<String, String>();
            requestHeaders.put("Authorization", "Bearer " + apiKey);
            request.headers = requestHeaders;
            request.baseUri = baseURI;
            request.endpoint = endpoint;
            request.method = Method.POST;
            request.body = array.toString();

            try {
                Response response = client.api(request);
                return ((response.statusCode) + "").contains("20");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean patch(JSONArray array) {
        if (array != null) {

        }
        return false;
    }



}
