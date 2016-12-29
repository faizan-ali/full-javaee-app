package co.workamerica.entities.candidates;

import co.workamerica.entities.criteria.Certifications;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.persistence.*;

/**
 * Created by Faizan on 7/26/2016.
 */
@Entity
@Table(name = "CandidateCertifications", schema = "workamericadb")
public class CandidateCertifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CandidateCertificationID")
    private int candidateCertificationID;
    @Column(name = "CandidateID")
    private int candidateID;
    @Column(name = "CertificationID")
    private int certificationID;
    @Column(name = "CompletionDate")
    private String completionDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CertificationID", referencedColumnName = "CertificationID", insertable = false, updatable = false)
    private Certifications certification;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CandidateID", referencedColumnName = "CandidateID", insertable = false, updatable = false)
    private Candidates candidate;

    public CandidateCertifications() {

    }

    public int getCandidateCertificationID() {
        return candidateCertificationID;
    }

    public void setCandidateCertificationID(int candidateCertificationID) {
        this.candidateCertificationID = candidateCertificationID;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public int getCertificationID() {
        return certificationID;
    }

    public void setCertificationID(int certificationID) {
        this.certificationID = certificationID;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public Certifications getCertification() {
        return certification;
    }

    public void setCertification(Certifications certificate) {
        this.certification = certificate;
    }

    public Candidates getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidates candidate) {
        this.candidate = candidate;
    }

    public boolean isObtained() {
        if (completionDate != null && completionDate.isEmpty() && (candidate.getDateCreated() == null || candidate.getDateCreated().isEmpty())) {
            return true;
        }
        return CustomUtilities.hasDatePassed(completionDate);
    }
}
