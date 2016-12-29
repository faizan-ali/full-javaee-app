package co.workamerica.functionality.clients.texts;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.texts.ConversationMessages;
import co.workamerica.entities.texts.Conversations;
import co.workamerica.functionality.persistence.CandidatePersistence;
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
 * Created by Faizan on 5/10/2016.
 */
@WebServlet("/ClientTextServlet")
public class ClientTextServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message") != null ? request.getParameter("message").trim() : "";
        HttpSession session = request.getSession();

        if (!message.isEmpty()) {
            Clients client = (Clients) session.getAttribute("user");

            if (request.getParameter("candidateID") != null && client != null) {
                int candidateID = Integer.parseInt(request.getParameter("candidateID"));

                try {
                    Candidates candidate = CandidatePersistence.getCandidateByID(candidateID);
                    boolean sent = Twilio.sendClientText(candidate.getPhone(), client.getAssignedNumber(), message);

                    if (!sent) {
                        session.setAttribute("employerError", "Error sending message");
                    } else {
                        session.setAttribute("employerSuccess", "Message sent!");
                    }

                    try {
                        Conversations conversation = ConversationDataAccessObject.createIfNone(client.getClientID(), candidateID);
                        if (conversation != null) {
                            ConversationMessages text = ConversationMessageDataAccessObject.create(conversation.getConversationID(), Clock.getCurrentDate(), Clock.getCurrentTime(),
                                    "Candidate", message, sent ? "Yes" : "No");
                            text.setConversation(conversation);
                            conversation.getMessages().add(text);
                            conversation.setClient(client);
                            conversation.setCandidate(candidate);

                            if (client.getConversationByID(conversation.getConversationID()) == null) {
                                client.getConversations().add(conversation);
                            } else {
                                client.getConversationByID(conversation.getConversationID()).getMessages().add(text);
                            }

                            session.setAttribute("user", client);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect("candidate-profile.jsp");
                } catch (Exception e) {
                    session.setAttribute("employerError", "Error sending message");
                    response.sendRedirect("candidate-profile.jsp");
                }
            } else {
                session.setAttribute("employerError", "Error sending message");
                response.sendRedirect("candidate-profile.jsp");
            }
        } else {
            session.setAttribute("employerError", "Message cannot be blank");
            response.sendRedirect("candidate-profile.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request, response);
    }
}
