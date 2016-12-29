package co.workamerica.entities.candidates;

import co.workamerica.entities.criteria.Fields;

import javax.persistence.*;

/**
 * Created by Faizan on 7/27/2016.
 */
@Entity
@Table(name = "CandidateFields", schema = "workamericadb")
public class CandidateFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CandidateFieldID")
    private int candidateFieldID;
    @Column(name = "FieldID")
    private int fieldID;
    @Column(name = "CandidateID")
    private int candidateID;
    @Column(name = "IsCurrent")
    private String isCurrent;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FieldID", referencedColumnName = "FieldID", insertable = false, updatable = false)
    private Fields field;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CandidateID", referencedColumnName = "CandidateID", insertable = false, updatable = false)
    private Candidates candidate;

    public CandidateFields() {

    }

    public int getCandidateFieldID() {
        return candidateFieldID;
    }

    public void setCandidateFieldID(int candidateFieldID) {
        this.candidateFieldID = candidateFieldID;
    }

    public int getFieldID() {
        return fieldID;
    }

    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public Fields getField() {
        return field;
    }

    public void setField(Fields field) {
        this.field = field;
    }

    public Candidates getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidates candidate) {
        this.candidate = candidate;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }
}
