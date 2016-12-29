package co.workamerica.tests.daos;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.texts.Conversations;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.ClientPersistence;
import co.workamerica.functionality.persistence.ConversationDataAccessObject;
import com.twilio.sdk.TwilioRestException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.NoResultException;
import java.util.HashMap;

/**
 * Created by Faizan on 5/18/2016.
 */

@Test (groups = {"dao", "conversations"})
public class TestConversations {

    ConversationDataAccessObject obj = new ConversationDataAccessObject();
    Clients client = new Clients();
    Candidates candidate = new Candidates();

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
    }

    @AfterClass
    public void cleanUp() {

    }

    public void createAndDelete() {
        Conversations conversation = obj.create(client.getClientID(), candidate.getCandidateID());
        assert conversation != null;
        assert conversation.getConversationID() > 0;
        assert conversation.getClientID() == client.getClientID();
        assert conversation.getCandidateID() == candidate.getCandidateID();
        assert conversation.getMessages().isEmpty();
        assert obj.delete(conversation.getConversationID());
    }

    public void createAndDeleteNull() {
        assert obj.create(-2, 0) == null;
        assert obj.create(1, -2) == null;
        assert obj.create (0, 0) == null;
        assert obj.create (-2, -100) == null;
        assert !obj.delete(-1);
        assert !obj.delete(0);
    }

    public void getByClientAndCandidateID() {
        Conversations testConversation = obj.create(client.getClientID(), candidate.getCandidateID());
        Conversations conversation = obj.getByClientAndCandidateID(client.getClientID(), candidate.getCandidateID());

        assert conversation != null;
        assert conversation.getConversationID() > 0;
        assert conversation.getConversationID() == testConversation.getConversationID();
        assert conversation.getClientID() == client.getClientID();
        assert conversation.getCandidateID() == candidate.getCandidateID();

        obj.delete(testConversation.getConversationID());
    }

    @Test (expectedExceptions = NoResultException.class)
    public void getByClientAndCandidateIDException() {
        obj.getByClientAndCandidateID(21342134, 2134234);
    }

    public void getByClientAndCandidateIDNull() {
        assert obj.getByClientAndCandidateID(-1, 0) == null;
        assert obj.getByClientAndCandidateID(2, -2) == null;
        assert obj.getByClientAndCandidateID(0, 0) == null;
        assert obj.getByClientAndCandidateID(-1, -10) == null;
    }

    public void createIfNone () {
        Conversations conversation = obj.createIfNone(client.getClientID(), candidate.getCandidateID());

        assert conversation != null;
        assert conversation.getConversationID() > 0;
        assert conversation.getClientID() == client.getClientID();
        assert conversation.getCandidateID() == candidate.getCandidateID();

        obj.delete(conversation.getConversationID());
    }

    // Checks whether the entity is returned if it already exists
    public void createIfNoneWhenExists() {
        Conversations testConversation = obj.create(client.getClientID(), candidate.getCandidateID());
        Conversations conversation = obj.createIfNone(client.getClientID(), candidate.getCandidateID());

        assert conversation != null;
        assert conversation.getConversationID() > 0;
        assert conversation.getConversationID() == testConversation.getConversationID();
        assert conversation.getClientID() == client.getClientID();
        assert conversation.getCandidateID() == candidate.getCandidateID();

        obj.delete(testConversation.getConversationID());
    }

    public void createIfNoneNull() {
        assert obj.createIfNone(-11, 0) == null;
        assert obj.createIfNone(-11, -10) == null;
        assert obj.createIfNone(11, 0) == null;
        assert obj.createIfNone(0, 0) == null;
    }

    public void getByID () {
        Conversations testConversation = obj.create(client.getClientID(), candidate.getCandidateID());
        Conversations conversation = obj.getByID(testConversation.getConversationID());

        assert conversation != null;
        assert conversation.getConversationID() > 0;
        assert conversation.getConversationID() == testConversation.getConversationID();
        assert conversation.getClientID() == client.getClientID();
        assert conversation.getCandidateID() == candidate.getCandidateID();
    }

    public void getByIDNull () {
        assert obj.getByID(-55) == null;
        assert obj.getByID(0) == null;
    }

}
