package co.workamerica.entities.texts;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.Clients;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Faizan on 5/10/2016.
 */
@Entity
@Table(name="Conversations",  schema = "workamericadb")
public class Conversations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConversationID")
    private int conversationID;
    @Column(name = "ClientID")
    private int clientID;
    @Column(name = "CandidateID")
    private int candidateID;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "ClientID", referencedColumnName = "ClientID", insertable = false, updatable = false)
    private Clients client;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "CandidateID", referencedColumnName = "CandidateID", insertable = false, updatable = false)
    private Candidates candidate;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.EAGER)
    private List<ConversationMessages> messages = new ArrayList<>();

    public List<ConversationMessages> getMessages() {
        return messages;
    }

    public void setMessages(List<ConversationMessages> messages) {
        this.messages = messages;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    public Candidates getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidates candidate) {
        this.candidate = candidate;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conversations that = (Conversations) o;

        if (conversationID != that.conversationID) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.conversationID);
    }
}
