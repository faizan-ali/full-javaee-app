package co.workamerica.functionality.clients.texts;

import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.texts.ConversationMessages;
import co.workamerica.entities.texts.Conversations;
import co.workamerica.functionality.persistence.ConversationDataAccessObject;
import co.workamerica.functionality.persistence.ConversationMessageDataAccessObject;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.twilio.API.Twilio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Faizan on 5/11/2016.
 */
@WebServlet("/ClientRespondServlet")
public class ClientRespondServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message") != null ? request.getParameter("message").trim() : "";
        int conversationID = request.getParameter("conversationID") != null ? Integer.parseInt(request.getParameter("conversationID")) : 0;
        HttpSession session = request.getSession();
        Clients client = (Clients) session.getAttribute("user");

        if (message.isEmpty() || conversationID <= 0 || client == null) {
            response.sendRedirect("outreach.jsp");
            session.setAttribute("clientError", "Error sending message");
        } else if (client.getAssignedNumber() == null || client.getAssignedNumber().isEmpty()) {
            response.sendRedirect("outreach.jsp");
            session.setAttribute("clientError", "Not authorized");
        } else {
            Conversations conversation = ConversationDataAccessObject.getByID(conversationID);

            if (conversation != null) {
                try {
                    boolean sent = Twilio.sendClientText(conversation.getCandidate().getPhone(), client.getAssignedNumber(), message);
                    if (!sent) {
                        session.setAttribute("clientError", "Error sending message");
                    } else {
                        session.setAttribute("clientError", "Message sent!");
                    }

                    ConversationMessages text = ConversationMessageDataAccessObject.create(conversation.getConversationID(), Clock.getCurrentDate(), Clock.getCurrentTime(),
                            "Candidate", message, sent ? "Yes" : "No");
                    conversation.getMessages().add(text);
                    if (client.getConversationByID(conversation.getConversationID()) != null) {
                        client.getConversationByID(conversation.getConversationID()).getMessages().add(text);
                    }
                    session.setAttribute("conversation", conversation);
                    session.setAttribute("user", client);
                    response.sendRedirect("outreach.jsp");
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("clientError", "Error sending message");
                    response.sendRedirect("outreach.jsp");
                }
            } else {
                session.setAttribute("clientError", "Error sending message");
                response.sendRedirect("outreach.jsp");
            }
        }
    }
}
