package co.workamerica.functionality.twilio.Webhooks;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.texts.ConversationMessages;
import co.workamerica.entities.texts.Conversations;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.ClientPersistence;
import co.workamerica.functionality.persistence.ConversationDataAccessObject;
import co.workamerica.functionality.shared.utilities.Clock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static co.workamerica.functionality.persistence.ConversationMessageDataAccessObject.create;

/**
 * Created by Faizan on 5/11/2016.
 */
@WebServlet("/TwilioReceiverServlet")
public class TwilioClientReceiverServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String phone = request.getParameter("From") != null ? request.getParameter("From").substring(2) : "",
                body = request.getParameter("Body") != null ? request.getParameter("Body") : "",
                recipient = request.getParameter("To") != null ? request.getParameter("To").substring(2) : "";

        try {
            Candidates candidate = CandidatePersistence.getCandidateByPhone(phone);
            Clients client = ClientPersistence.getByPhone(recipient);

            if (candidate != null && client != null) {
                Conversations conversation = ConversationDataAccessObject.getByClientAndCandidateID(client.getClientID(), candidate.getCandidateID());
                if (conversation != null) {
                    ConversationMessages message = create(conversation.getConversationID(), Clock.getCurrentDate(), Clock.getCurrentTime(), "Client", body, "Yes");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
