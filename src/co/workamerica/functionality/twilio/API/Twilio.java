package co.workamerica.functionality.twilio.API;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.candidates.jsonModels.apiLogs.APILogs;
import co.workamerica.entities.candidates.jsonModels.apiLogs.twilio.TwilioWelcome;
import co.workamerica.entities.candidates.jsonModels.apiLogs.twilio.TwilioWelcomeEvents;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Twilio {

    static final String accountSID = "";
    static final String authToken = "";
    static final String welcomeMessageSid = "";

    public Twilio() {

    }

    public static boolean sendClientText(String recipient, String from, String message) throws TwilioRestException, InterruptedException {
        if (recipient != null && message != null && from != null && !from.isEmpty() && !recipient.isEmpty() && !message.isEmpty()) {
            TwilioRestClient client = new TwilioRestClient(accountSID, authToken);

            recipient = recipient.contains("+1") ? recipient : "+1" + recipient;
            from = from.contains("+1") ? from : "+1" + from;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", message));
            params.add(new BasicNameValuePair("To", recipient));
            params.add(new BasicNameValuePair("From", from));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message text = messageFactory.create(params);
            if (text.getErrorCode() != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean sendText(String number, String email, String password, String name, String type) throws TwilioRestException, InterruptedException {

        if (number != null && number.length() == 10) {
            TwilioRestClient client = new TwilioRestClient(accountSID, authToken);

            if (email.length() < 6) {
                email = number;
            }

            String welcomeBody = "Hi " + name
                    + ", it's Laura from WorkAmerica. Your job seeker profile has been created, employers can now contact you with available job opportunities! "
                    + "Your login is " + email + " and password is " + password + ". Log in to update your profile at www.workamerica.co/login or contact us at info@workamerica.co with any issues";

            String forgotBody = "Hi " + name
                    + ", Your new password is " + password + " . Login at www.workamerica.co/login to update your profile!";

            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To", number));
            params.add(new BasicNameValuePair("From", "+1NUMBER_HERE"));
            params.add(new BasicNameValuePair("MessagingServiceSid", welcomeMessageSid));

            if (type != null && type.equalsIgnoreCase("forgot")) {
                params.add(new BasicNameValuePair("Body", forgotBody));
            } else {
                params.add(new BasicNameValuePair("Body", welcomeBody));
            }
            MessageFactory messageFactory = client.getAccount().getMessageFactory();

            try {
                Message response = messageFactory.create(params);
                if (response.getErrorCode() == null) {
                    return true;
                }
            } catch (TwilioRestException e) {
                e.printStackTrace();

                try {
                    String simpleNumber = CustomUtilities.cleanNumber(number.substring(2));
                    Candidates candidate = CandidatePersistence.getCandidateByPhone(simpleNumber);

                    if (candidate != null) {
                        TwilioWelcomeEvents event = new TwilioWelcomeEvents();
                        TwilioWelcome twilioWelcome = new TwilioWelcome();
                        APILogs apiLogs = candidate.getApiLogs();

                        if (apiLogs != null) {
                            twilioWelcome.setPhone(simpleNumber);
                            twilioWelcome.setBody(welcomeBody);
                            twilioWelcome.setMessageID("");
                            twilioWelcome.setSuccess("False");

                            event.setTime(Clock.getCurrentTime());
                            event.setDate(Clock.getCurrentDate());
                            event.setStatus("Failed");
                            event.setError("Exception");

                            twilioWelcome.addToTwilioWelcomeEvents(event);
                            apiLogs.addToTwilioWelcome(twilioWelcome);
                            candidate.setApiLogs(apiLogs);
                            CandidatePersistence.merge(candidate);
                        }
                    }
                } catch (Exception f) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
