package co.workamerica.functionality.sendgrid.Webhooks;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.candidates.jsonModels.apiLogs.APILogs;
import co.workamerica.entities.candidates.jsonModels.apiLogs.sendgrid.DeliveryEvents;
import co.workamerica.entities.candidates.jsonModels.apiLogs.sendgrid.EngagementEvents;
import co.workamerica.entities.candidates.jsonModels.apiLogs.sendgrid.SendGridWelcome;
import co.workamerica.entities.candidates.jsonModels.apiLogs.twilio.TwilioWelcome;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.shared.utilities.Clock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Faizan on 6/24/2016.
 */
@WebServlet(name = "SendGridWebhook", urlPatterns = {"/sendgrid"})
public class SendGridWebhook extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder stringBuffer = new StringBuilder();
        String data = null;

        try {
            BufferedReader reader = request.getReader();
            while ((data = reader.readLine()) != null) {
                stringBuffer.append(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONArray responseArray = new JSONArray(stringBuffer.toString());

            for (Object obj : responseArray) {
                JSONObject responseObject = (JSONObject) obj;

                String email = responseObject.getString("email");

                if (email != null && !email.isEmpty()) {
                    Candidates candidate = CandidatePersistence.getCandidateByEmail(email);

                    if (candidate != null) {
                        String event = responseObject.getString("event");
                        String date = Clock.unixTimestampToDate(responseObject.getLong("timestamp")) + "";
                        String time = Clock.unixTimestampToTime(responseObject.getLong("timestamp")) + "";
                        String emailID = responseObject.getString("sg_message_id");
                        String userAgent = "";

                        APILogs apiLogs = candidate.getApiLogs();
                        DeliveryEvents deliveryEvent = new DeliveryEvents();
                        EngagementEvents engagementEvent = new EngagementEvents();

                        deliveryEvent.setTime(time);
                        deliveryEvent.setDate(date);
                        deliveryEvent.setEvent(event);

                        engagementEvent.setTime(time);
                        engagementEvent.setDate(date);
                        engagementEvent.setEvent(event);
                        engagementEvent.setUserAgent(userAgent);

                        if (apiLogs != null) {
                            ArrayList<SendGridWelcome> welcomes = apiLogs.getSendGridWelcomes();

                            if (welcomes != null) {
                                for (SendGridWelcome welcome : welcomes) {
                                    if (welcome.getEmailID().equals(emailID)) {
                                        ArrayList<DeliveryEvents> deliveryEvents = welcome.getDeliveryEvents() == null ? new ArrayList<>() : welcome.getDeliveryEvents();
                                        ArrayList<EngagementEvents> engagementEvents = welcome.getEngagementEvents() == null ? new ArrayList<>() : welcome.getEngagementEvents();

                                        if (event != null && (event.equals("bounce") || event.equals("deferred") || event.equals("delivered") ||
                                                event.equals("dropped") || event.equals("processed"))) {
                                            deliveryEvents.add(deliveryEvent);
                                            if (event.equals("delivered")) {
                                                welcome.setSuccess("True");
                                            } else {
                                                welcome.setSuccess("False");
                                            }
                                        } else {
                                            welcome.setSuccess("True");
                                            try {
                                                userAgent = responseObject.getString("useragent");
                                            } catch (JSONException e) {

                                            }
                                            engagementEvent.setUserAgent(userAgent);
                                            engagementEvents.add(engagementEvent);
                                        }
                                        welcome.setDeliveryEvents(deliveryEvents);
                                        welcome.setEngagementEvents(engagementEvents);
                                    }
                                }
                                apiLogs.setSendGridWelcomes(welcomes);
                            } else {
                                welcomes = new ArrayList<SendGridWelcome>();
                                SendGridWelcome welcome = new SendGridWelcome();
                                ArrayList<DeliveryEvents> deliveryEvents = new ArrayList<DeliveryEvents>();
                                ArrayList<EngagementEvents> engagementEvents = new ArrayList<EngagementEvents>();

                                welcome.setEmailID(emailID);

                                if (event != null && (event.equals("bounce") || event.equals("deferred") || event.equals("delivered") ||
                                        event.equals("dropped") || event.equals("processed"))) {
                                    deliveryEvents.add(deliveryEvent);
                                    if (event.equals("delivered")) {
                                        welcome.setSuccess("True");
                                    } else {
                                        welcome.setSuccess("False");
                                    }
                                } else {
                                    welcome.setSuccess("True");
                                    userAgent = responseObject.getString("useragent");
                                    engagementEvent.setUserAgent(userAgent);
                                    engagementEvents.add(engagementEvent);
                                }
                                welcome.setDeliveryEvents(deliveryEvents);
                                welcome.setEngagementEvents(engagementEvents);
                                apiLogs.addToSendGridWelcomes(welcome);
                            }
                        } else {
                            apiLogs = new APILogs();
                            ArrayList<TwilioWelcome> twilioWelcomes = new ArrayList<TwilioWelcome>();
                            ArrayList<SendGridWelcome> sendGridWelcomes = new ArrayList<SendGridWelcome>();
                            ArrayList<DeliveryEvents> deliveryEvents = new ArrayList<DeliveryEvents>();
                            ArrayList<EngagementEvents> engagementEvents = new ArrayList<EngagementEvents>();
                            SendGridWelcome welcome = new SendGridWelcome();
                            welcome.setEmailID(emailID);

                            if (event != null && (event.equals("bounce") || event.equals("deferred") || event.equals("delivered") ||
                                    event.equals("dropped") || event.equals("processed"))) {
                                deliveryEvents.add(deliveryEvent);
                                if (event.equals("delivered")) {
                                    welcome.setSuccess("True");
                                } else {
                                    welcome.setSuccess("False");
                                }
                            } else {
                                welcome.setSuccess("True");
                                userAgent = responseObject.getString("useragent");
                                engagementEvent.setUserAgent(userAgent);
                                engagementEvents.add(engagementEvent);
                            }

                            welcome.setDeliveryEvents(deliveryEvents);
                            welcome.setEngagementEvents(engagementEvents);
                            sendGridWelcomes.add(welcome);
                            apiLogs.setSendGridWelcomes(sendGridWelcomes);
                            apiLogs.setTwilioWelcomes(twilioWelcomes);
                        }
                        candidate.setApiLogs(apiLogs);
                        CandidatePersistence.merge(candidate);
                    }
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{message:success}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{message:failure}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
