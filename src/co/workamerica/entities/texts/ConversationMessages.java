package co.workamerica.entities.texts;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Faizan on 5/10/2016.
 */
@Entity
@Table(name="ConversationMessages",  schema = "workamericadb")
public class ConversationMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConversationMessageID")
    private int conversationMessageID;
    @Column(name = "ConversationID")
    private int conversationID;
    @Column(name = "Date")
    private String date;
    @Column(name = "Time")
    private String time;
    @Column(name = "Recipient")
    private String recipient;
    @Column(name = "Body")
    private String body;
    @Column(name = "Success")
    private String success;

    @ManyToOne
    @JoinColumn(name = "ConversationID", referencedColumnName = "ConversationID", insertable = false, updatable = false)
    private Conversations conversation;

    public Conversations getConversation() {
        return conversation;
    }

    public void setConversation(Conversations conversation) {
        this.conversation = conversation;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getConversationMessageID() {
        return conversationMessageID;
    }

    public void setConversationMessageID(int conversationMessageID) {
        this.conversationMessageID = conversationMessageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationMessages that = (ConversationMessages) o;
        if (conversationMessageID != that.conversationMessageID) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.conversationMessageID);
    }
}
