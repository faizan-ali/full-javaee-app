package co.workamerica.functionality.mailchimp;

import co.workamerica.functionality.shared.utilities.CustomUtilities;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Faizan on 7/15/2016.
 */
public class MailChimpNewsletter {

    private final static String apiKey = "";
    private final static String contentType = "application/json";
    private final static String requestURL = "https://us7.api.mailchimp.com/3.0/lists/-/members";

    public static void add (String email) {
        if (CustomUtilities.isValidEmail(email)) {
            JSONObject jsonObject = new JSONObject();
            JSONObject mergeFields = new JSONObject();
            jsonObject.put("email_address", email);
            jsonObject.put("status", "subscribed");
            mergeFields.put("FNAME", "");
            mergeFields.put("LNAME", "");
            mergeFields.put("TARGET", "");
            mergeFields.put("ORG", "");
            mergeFields.put("STATE", "");
            jsonObject.put("MERGE_FIELDS", mergeFields);

            try {
                URL url = new URL(requestURL);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", "apikey " + apiKey);
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                DataOutputStream stream = new DataOutputStream(con.getOutputStream());
                stream.write(jsonObject.toString().getBytes());
                stream.flush();
                stream.close();

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
