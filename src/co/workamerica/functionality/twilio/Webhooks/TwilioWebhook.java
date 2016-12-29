package co.workamerica.functionality.twilio.Webhooks;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.candidates.jsonModels.apiLogs.APILogs;
import co.workamerica.entities.candidates.jsonModels.apiLogs.twilio.TwilioWelcome;
import co.workamerica.entities.candidates.jsonModels.apiLogs.twilio.TwilioWelcomeEvents;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.shared.utilities.Clock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Faizan on 6/27/2016.
 */
@WebServlet(name = "TwilioWebhook", urlPatterns = {"/twiliowebhook"})
public class TwilioWebhook extends HttpServlet {
    static final String welcomeMessageSid = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String phone = request.getParameter("To").substring(2), messageID = request.getParameter("MessageSid"),
                    body = request.getParameter("Body"), status = request.getParameter("MessageStatus"), date = Clock.getCurrentDate(),
                    time = Clock.getCurrentTime(), error = request.getParameter("ErrorCode") == null ? "" : request.getParameter("ErrorCode");

            Candidates candidate = CandidatePersistence.getCandidateByPhone(phone);

            try {
                if (candidate != null) {
                    TwilioWelcomeEvents event = new TwilioWelcomeEvents();
                    event.setDate(date);
                    event.setTime(time);
                    event.setStatus(status);
                    event.setError(error);

                    APILogs apiLogs = candidate.getApiLogs();

                    if (apiLogs != null) {
                        ArrayList<TwilioWelcome> twilioWelcomes = apiLogs.getTwilioWelcomes();
                        if (twilioWelcomes != null) {
                            boolean exists = false;
                            for (TwilioWelcome twilioWelcome : twilioWelcomes) {
                                if (twilioWelcome.getMessageID() != null &&
                                        twilioWelcome.getMessageID().equals(messageID)) {
                                    twilioWelcome.setSuccess(error.isEmpty() ? "True" : "False");
                                    twilioWelcome.addToTwilioWelcomeEvents(event);
                                    exists = true;
                                    break;
                                }
                            }

                            if (!exists) {
                                TwilioWelcome twilioWelcome = new TwilioWelcome();
                                twilioWelcome.setPhone(phone);
                                twilioWelcome.setMessageID(messageID);
                                twilioWelcome.setBody(body);
                                twilioWelcome.setSuccess(error.isEmpty() ? "True" : "False");
                                twilioWelcome.addToTwilioWelcomeEvents(event);
                                twilioWelcomes.add(twilioWelcome);
                            }
                        }
                    } else {
                        apiLogs = new APILogs();
                        ArrayList<TwilioWelcome> twilioWelcomes = new ArrayList<TwilioWelcome>();
                        TwilioWelcome twilioWelcome = new TwilioWelcome();
                        twilioWelcome.setPhone(phone);
                        twilioWelcome.setMessageID(messageID);
                        twilioWelcome.setBody(body);
                        twilioWelcome.setSuccess(error.isEmpty() ? "True" : "False");
                        twilioWelcome.addToTwilioWelcomeEvents(event);
                        twilioWelcomes.add(twilioWelcome);
                        apiLogs.setTwilioWelcomes(twilioWelcomes);
                    }
                    candidate.setApiLogs(apiLogs);
                    CandidatePersistence.merge(candidate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception g) {
            g.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
