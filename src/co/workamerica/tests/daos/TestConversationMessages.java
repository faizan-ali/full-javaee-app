package co.workamerica.tests.daos;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.texts.ConversationMessages;
import co.workamerica.entities.texts.Conversations;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.ClientPersistence;
import co.workamerica.functionality.persistence.ConversationDataAccessObject;
import co.workamerica.functionality.persistence.ConversationMessageDataAccessObject;
import co.workamerica.functionality.shared.utilities.Clock;
import com.twilio.sdk.TwilioRestException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by Faizan on 5/18/2016.
 */

@Test(groups = {"dao", "conversationMessages"})
public class TestConversationMessages {

    ConversationMessageDataAccessObject obj = new ConversationMessageDataAccessObject();
    Clients client = new Clients();
    Candidates candidate = new Candidates();
    Conversations conversation = new Conversations();

    @BeforeClass
    public void setUp() throws TwilioRestException {
        HashMap<String, String> candidateMap = new HashMap<String, String>();
        candidateMap.put("firstName", "John");
        candidateMap.put("lastName", "Smith");
        candidate = CandidatePersistence.createCandidate(candidateMap);

        HashMap<String, String> clientMap = new HashMap<String, String>();
        clientMap.put("firstName", "Test");
        clientMap.put("lastName", "Testy");
        client = ClientPersistence.createClient(clientMap);

        conversation = ConversationDataAccessObject.create(client.getClientID(), candidate.getCandidateID());
    }

    public void createAndDelete() {
        ConversationMessages message = obj.create(conversation.getConversationID(), Clock.getCurrentDate(), Clock.getCurrentTime(), "Candidate", "Test", "Yes");
        assert message != null;
        assert message.getConversationMessageID() > 0;
        assert message.getConversationID() == conversation.getConversationID();
        assert message.getBody().equals("Test");
        assert message.getRecipient().equals("Candidate");
        assert message.getSuccess().equals("Yes");
        assert obj.delete(message.getConversationMessageID());
    }

    public void createAndDeleteNull () {
        assert obj.create(0, Clock.getCurrentDate(), Clock.getCurrentTime(), "Candidate", "Test", "Yes") == null;
        assert obj.create(0, Clock.getCurrentDate(), Clock.getCurrentTime(), "Candidate", null, "Yes") == null;
        assert obj.create(2, Clock.getCurrentDate(), Clock.getCurrentTime(), "Candidate", "Test", "Yes") != null;
        assert obj.create(-1, Clock.getCurrentDate(), Clock.getCurrentTime(), "Candidate", "Test", "Yes") == null;
        assert obj.create(-10, Clock.getCurrentDate(), Clock.getCurrentTime(), "Candidate", null, "Yes") == null;
    }
}
