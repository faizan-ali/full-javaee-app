package co.workamerica.entities.texts;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Faizan on 5/10/2016.
 */
@Entity
@Table(name = "ReceivedTexts",  schema = "workamericadb")
public class ReceivedTexts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receivedTextID;
    @Column(name = "CandidateID")
    private int candidateID;
    @Column(name = "Date")
    private String date;
    @Column(name = "Recipient")
    private String recipient;
    @Column(name = "Body")
    private String body;

    @Override
    public boolean equals (Object obj) {
        try {
            ReceivedTexts receivedTexts;
            if (obj == null || (receivedTexts = ((ReceivedTexts) obj)).getReceivedTextID() <= 0) {
                return false;
            } else {
                return (this.getReceivedTextID() == receivedTexts.getReceivedTextID());
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode () {
        return Objects.hash(this.getReceivedTextID());
    }

    public int getReceivedTextID() {
        return receivedTextID;
    }

    public void setReceivedTextID(int receivedTextID) {
        this.receivedTextID = receivedTextID;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
