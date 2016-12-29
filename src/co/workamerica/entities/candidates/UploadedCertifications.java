package co.workamerica.entities.candidates;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: Administrators
 */
//Tells JPA that this class defines an entity to be stored in a database
@Entity
//Overrides default table name
@Table(name = "UploadedCertifications",  schema = "workamericadb")
public class UploadedCertifications implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UploadedCertificationID")
    private int uploadedCertificationID;
    @Column(name = "CandidateID")
    private int candidateID;
    @Column(name = "Data")
    private byte[] data;
    @Column(name = "FileFormat")
    private String fileFormat;
    @Column(name = "DateAcquired")
    private String dateAcquired;
    @Column(name = "DateExpiration")
    private String dateExpiration;
    @Column(name = "CertifyingBody")
    private String certifyingBody;
    @Column(name = "Type")
    private String type;
    @Column(name = "Field")
    private String field;
    @Column(name = "LocationAcquired")
    private String locationAcquired;
    @Column(name = "CertificationNumber")
    private String certificationNumber;
    @Column(name = "Description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "CandidateID", referencedColumnName = "CandidateID", insertable = false, updatable = false)
    private Candidates candidate;

    public Candidates getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidates candidate) {
        this.candidate = candidate;
    }

    public int getCertificationID() {
        return uploadedCertificationID;
    }

    public void setUploadedCertificationID(int uploadedCertificationID) {
        this.uploadedCertificationID = uploadedCertificationID;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(String dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getCertifyingBody() {
        return certifyingBody;
    }

    public void setCertifyingBody(String certifyingBody) {
        this.certifyingBody = certifyingBody;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setLocationAcquired(String locationAcquired) {
        this.locationAcquired = locationAcquired;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public int getUploadedCertificationID() {
        return uploadedCertificationID;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getLocationAcquired() {
        return locationAcquired;
    }

    public void setlocationAcquired(String locationAcquired) {
        this.locationAcquired = locationAcquired;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
