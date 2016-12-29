package co.workamerica.api.webhooks;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.functionality.aws.s3.S3Object;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.SchoolDataAccessObject;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by Faizan on 7/25/2016.
 */
@Path("/webhooks")
@RolesAllowed("Partner")
public class Partners {

    @POST
    @Path("/jefferson")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public String consume(InputStream stream) {
        try {
            JSONObject obj1 = CustomUtilities.XMLToJSONObject(S3Object.webHookDump(stream, "Jefferson", ".xml"));
            System.out.println(obj1.toString());
            JSONObject json = obj1.getJSONObject("students");

            try {
                JSONArray array = json.getJSONArray("student");

                for (int i = 0; i < array.length(); i++) {
                 //   generate(array.getJSONObject(i));
                }
            } catch (JSONException e) {
                JSONObject obj = json.getJSONObject("student");
              //  generate(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{message:success}";
    }

    private void generate (JSONObject json, String schoolName) {
        if (json != null && json.length() > 0) {
            try {
                String firstName = json.getString("first-name"), lastName = json.getString("last-name"), zip = json.getInt("zip") + "",
                        email = json.getString("email"), phone = "", certificationName = "", program = "", completionDate = "";
                JSONObject credentialsObject = json.getJSONObject("credentials");

                try {
                    phone = CustomUtilities.cleanNumber(json.getLong("phone") + "");
                } catch (JSONException e) {
                    phone = CustomUtilities.cleanNumber(json.getString("phone"));
                }

                HashMap<String, String> details = new HashMap<>();
                details.put("firstName", firstName);
                details.put("lastName", lastName);
                details.put("zip", zip);
                details.put("email", email);
                details.put("phone", phone);

                try {
                    JSONArray credentials = credentialsObject.getJSONArray("credential");
                    certificationName = credentials.getJSONObject(0).getString("name");
                    program = credentials.getJSONObject(0).getString("program");
                    completionDate = credentials.getJSONObject(0).getString("completion-date");
                } catch (JSONException e) {
                    JSONObject credentials = credentialsObject.getJSONObject("credential");
                    certificationName = credentials.getString("name");
                    program = credentials.getString("program");
                    completionDate = credentials.getString("completion-date");
                }

                Schools school = SchoolDataAccessObject.getSchoolByName(schoolName);

                details.put("school", school.getName());
                details.put("schoolID", school.getSchoolID() + "");
                details.put("anticipatedCertifications", certificationName);
                details.put("fields", program);
                details.put("completionDate", completionDate);
                details.put("accountOriginSourceID", school.getSchoolID() + "");
                details.put("accountOriginSourceType", "Webhook");
                details.put("workAmericaCreated", "No");

                if (CandidatePersistence.checkIfExistsByEmail(email)) {
                    Candidates candidate = CandidatePersistence.getCandidateByEmail(email);
                    CandidatePersistence.updateCandidate(details, candidate);
                } else {
                    CandidatePersistence.createCandidate(details);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GET
    @Path("/*")
    public String get() {
        return "{message: Incorrect HTTP method}";
    }
}
