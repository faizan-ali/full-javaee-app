package co.workamerica.functionality.twilio.Webhooks;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.texts.ReceivedTexts;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.ReceivedTextDataAccessObject;
import co.workamerica.functionality.shared.utilities.Clock;
import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class TwilioReceiverServlet
 */
@WebServlet(name = "/TwilioReceiverServlet", urlPatterns = {"/unmonitored"})
public class TwilioReceiverServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TwilioReceiverServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String phone = request.getParameter("From") != null ? request.getParameter("From").substring(2) : "",
                body = request.getParameter("Body") != null ? request.getParameter("Body") : "",
                date = request.getParameter("Date_created") != null ? request.getParameter("Date_created") : Clock.getCurrentDate(),
                recipient = request.getParameter("To") != null ? request.getParameter("To").substring(2) : "";

        if (!phone.isEmpty()) {

            Candidates candidate = CandidatePersistence.getCandidateByPhone(phone);

            if (candidate != null) {
                ReceivedTexts text = new ReceivedTexts();
                text.setCandidateID(candidate.getCandidateID());
                text.setDate(date);
                text.setBody(body);
                text.setRecipient(recipient);

                try {
                    ReceivedTextDataAccessObject.persist(text);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            TwiMLResponse twiml = new TwiMLResponse();
            Message message = new Message("Hello" + (candidate != null ? " " + candidate.getFirstName() : "") + ", this is an unmonitored number. Please contact us at info@workamerica.co or 877-750-2968, ext 2 with any questions.");
            try {
                twiml.append(message);
            } catch (TwiMLException e) {
                e.printStackTrace();
            }

            response.setContentType("application/xml");
            response.getWriter().print(twiml.toXML());
        }
    }

}
