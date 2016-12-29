package co.workamerica.functionality.twilio.API;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.AvailablePhoneNumber;
import com.twilio.sdk.resource.list.AvailablePhoneNumberList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Faizan on 5/10/2016.
 * https://www.twilio.com/docs/api/rest/available-phone-numbers
 */
public class TwilioNumberPurchaser  {
    static final String accountSID = "";
    static final String authToken = "";

    public static String getNewNumber() throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(accountSID, authToken);

        // Build a filter for the AvailablePhoneNumberList
        Map<String, String> params = new HashMap<String, String>();
        params.put("SmsEnabled", "true");

        AvailablePhoneNumberList numbers = client.getAccount().getAvailablePhoneNumbers(params, "US", "Local");
        List<AvailablePhoneNumber> list = numbers.getPageData();

        // Purchase the first number in the list.
        List<NameValuePair> purchaseParams = new ArrayList<NameValuePair>();
        purchaseParams.add(new BasicNameValuePair("PhoneNumber", list.get(0).getPhoneNumber()));
        purchaseParams.add(new BasicNameValuePair("SmsUrl", "http://platform.workamerica.co/TwilioReceiverServlet"));
        return client.getAccount().getIncomingPhoneNumberFactory().create(purchaseParams).getPhoneNumber().substring(2);
    }
}
