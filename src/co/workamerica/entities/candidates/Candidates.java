package co.workamerica.entities.candidates;

import co.workamerica.entities.candidates.jsonModels.accountOrigin.AccountOrigin;
import co.workamerica.entities.candidates.jsonModels.apiLogs.APILogs;
import co.workamerica.entities.clients.ClientPipelines;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.logs.candidates.CandidateLoginLogs;
import co.workamerica.entities.logs.schools.SchoolActivityLogs;
import co.workamerica.entities.texts.Conversations;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "Candidates", schema = "workamericadb")
@XmlRootElement
public class Candidates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CandidateID")
    private int candidateID;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "City")
    private String city;
    @Column(name = "State")
    private String state;
    @Column(name = "Zip")
    private String zip;
    @Column(name = "Latitude")
    private String latitude;
    @Column(name = "Longitude")
    private String longitude;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;
    @Column(name = "Salt")
    private String salt;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "AlternatePhone")
    private String alternatePhone;
    @Column(name = "Veteran")
    private String veteran;
    @Column(name = "AdditionalInformation")
    private String additionalInformation;
    @Column(name = "WorkExperience")
    private String workExperience;
    @Column(name = "Employed")
    private String employed;
    @Column(name = "Relocate")
    private String relocate;
    @Column(name = "PastEducation")
    private String pastEducation;
    @Column(name = "DateCreated")
    private String dateCreated;
    @Lob
    @Column(name = "Resume")
    private byte[] resume;
    @Lob
    @Column(name = "Photo")
    private byte[] photo;
    // This temporary String denotes the pipeline status of this candidate in
    // search results
    @Transient
    private String pipelineStatus = "Unranked";

    @OneToMany(mappedBy = "candidate")
    private List<UploadedCertifications> uploadedCertifications;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<ClientPipelines> pipeline;
    @Column(name = "School")
    private String school;
    // 1 = Not currently enrolled, Other
    @Column(name = "SchoolID")
    private int schoolID;
    @Column(name = "Authorized")
    private String authorized;
    @Column(name = "Approved")
    private String approved;
    @Column(name = "WorkAmericaCreated")
    private String workAmericaCreated;
    @Column(name = "TimeCreated")
    private String timeCreated;
    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private List<CandidateLoginLogs> loginLog;
    @ManyToOne
    @JoinColumn(name = "SchoolID", referencedColumnName = "SchoolID", insertable = false, updatable = false)
    private Schools schoolEntity;
    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Conversations> conversations = new ArrayList<Conversations>();
    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<CandidateCertifications> certifications = new ArrayList<CandidateCertifications>();
    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<CandidateFields> fields = new ArrayList<CandidateFields>();

    //Education fields
    @Column(name = "Field")
    private String field;
    @Column(name = "AnticipatedCertification")
    private String anticipatedCertification;
    @Column(name = "CompletionDate")
    private String completionDate;
    @Column(name = "PastField")
    private String pastField;
    @Column(name = "ObtainedCertification")
    private String obtainedCertification;
    @Column(name = "ValidDriversLicense")
    private String validDriversLicense;
    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private List<SchoolActivityLogs> schoolActivityLog;
    @Column(name = "ResumeDetails")
    private String resumeDetails;
    @Column(name = "AccountOrigin")
    private String accountOrigin;
    @Column(name = "APILogs")
    private String apiLogs;

    public Candidates() {

    }


    @Override
    public boolean equals(Object obj) {
        try {
            Candidates candidate;
            if (obj == null || (candidate = ((Candidates) obj)).getCandidateID() <= 0) {
                return false;
            } else {
                return (this.candidateID == candidate.getCandidateID())
                        && this.email.equals(candidate.getEmail());
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.candidateID, this.email);
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public String getVeteran() {
        return veteran;
    }

    public void setVeteran(String veteran) {
        this.veteran = veteran;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getEmployed() {
        return employed;
    }

    public void setEmployed(String employed) {
        this.employed = employed;
    }

    public String getRelocate() {
        return relocate;
    }

    public void setRelocate(String relocate) {
        this.relocate = relocate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getPastEducation() {
        return pastEducation;
    }

    public void setPastEducation(String pastEducation) {
        this.pastEducation = pastEducation;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    // Adds to current pipeline, returns false if candidate already exists in
    // pipeline.
    public boolean addToClientPipeline(ClientPipelines elt) {

        if (this.pipeline == null) {
            this.pipeline = new ArrayList<ClientPipelines>();
        }

        if (!this.pipeline.contains(elt)) {
            this.pipeline.add(elt);
            elt.setCandidate(this);
            return false;
        }
        return true;
    }

    // Removes from current pipeline, returns false if candidate does not exist
    // in pipeline.
    public boolean removeFromClientPipeline(ClientPipelines elt) {

        if (this.pipeline.remove(elt)) {
            elt.setCandidate(null);
            return true;
        }
        return false;
    }

    public String getPipelineStatus() {
        return pipelineStatus;
    }

    public void setPipelineStatus(String pipelineStatus) {
        this.pipelineStatus = pipelineStatus;
    }

    public List<ClientPipelines> getPipeline() {
        return pipeline;
    }

    public void setPipeline(List<ClientPipelines> pipeline) {
        this.pipeline = pipeline;
    }

    public String getSchool() {
        if (this.getSchoolEntity() != null) {
            if (!this.schoolEntity.getName().isEmpty()) {
                return this.schoolEntity.getName();
            }
        }
        return this.school == null ? "" : this.school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }

    public List<CandidateLoginLogs> getLoginLog() {
        return loginLog;
    }

    public List<UploadedCertifications> getUploadedCertifications() {
        return uploadedCertifications;
    }

    public void setUploadedCertifications(List<UploadedCertifications> uploadedCertifications) {
        this.uploadedCertifications = uploadedCertifications;
    }

    public void setLoginLog(List<CandidateLoginLogs> loginLog) {
        this.loginLog = loginLog;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getWorkAmericaCreated() {
        return workAmericaCreated;
    }

    public void setWorkAmericaCreated(String workAmericaCreated) {
        this.workAmericaCreated = workAmericaCreated;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getAnticipatedCertification() {
        return anticipatedCertification;
    }

    public void setAnticipatedCertification(String anticipatedCertification) {
        this.anticipatedCertification = anticipatedCertification;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getPastField() {
        return pastField;
    }

    public void setPastField(String pastField) {
        this.pastField = pastField;
    }

    public String getObtainedCertification() {
        return obtainedCertification;
    }

    public void setObtainedCertification(String obtainedCertification) {
        this.obtainedCertification = obtainedCertification;
    }

    public String getValidDriversLicense() {
        return validDriversLicense;
    }

    public void setValidDriversLicense(String validDriversLicense) {
        this.validDriversLicense = validDriversLicense;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public Schools getSchoolEntity() {
        return schoolEntity;
    }

    public List<Conversations> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversations> conversations) {
        this.conversations = conversations;
    }

    public String getLastLoginDate() {
        if (this.loginLog != null && !this.loginLog.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

                Date date = dateFormat.parse(this.loginLog.get(0).getDate());

                for (CandidateLoginLogs log : this.getLoginLog()) {
                    Date temp = dateFormat.parse(log.getDate());
                    if (temp.after(date)) {
                        date = temp;
                    }
                }
                return dateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public CandidateLoginLogs getLastLoginLog () {
        if (this.getLoginLog() != null && !this.getLoginLog().isEmpty()) {
            String lastDate = getLastLoginDate();

            if (!lastDate.isEmpty()) {
                for (CandidateLoginLogs loginLog : this.loginLog) {
                    if (loginLog.getDate().equals(lastDate)) {
                        return loginLog;
                    }
                }
            }
        }
        return null;
    }


    public AccountOrigin getAccountOrigin() {
        if (accountOrigin != null) {
            try {
                Gson gson = new Gson();
                return gson.fromJson(accountOrigin, AccountOrigin.class);
            } catch (JsonSyntaxException e) {}
        }
        return null;
    }

    public void setAccountOrigin(AccountOrigin accountOrigin) {
        if (accountOrigin != null) {
            Gson gson = new Gson();
            String json = gson.toJson(accountOrigin);
            setAccountOrigin(json);
        }
    }

    public void setAccountOrigin(String accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public APILogs getApiLogs() {
        if (apiLogs != null) {
            try {
                Gson gson = new Gson();
                return gson.fromJson(apiLogs, APILogs.class);
            } catch (JsonSyntaxException e) {}
        }
        return null;
    }

    public void setApiLogs(String apiLogs) {
        this.apiLogs = apiLogs;
    }

    public void setApiLogs (APILogs apiLogs) {
        if (apiLogs != null) {
            Gson gson = new Gson();
            String json = gson.toJson(apiLogs);
            setApiLogs(json);
        }
    }

    public List<CandidateCertifications> getCandidateCertifications() {
        return certifications;
    }

    public void setCandidateCertifications(List<CandidateCertifications> certifications) {
        this.certifications = certifications;
    }

    public void updateCertificationsByListAndDate(ArrayList<Integer> list, String date) {
        if (list != null && !list.isEmpty() && date != null) {
            List<CandidateCertifications> certificationList =
                    certifications == null || certifications.isEmpty() ? new ArrayList<>() : certifications;

            for (int certificationID : list) {
                CandidateCertifications cert = new CandidateCertifications();
                cert.setCandidateID(candidateID);
                cert.setCertificationID(certificationID);
                cert.setCompletionDate(date);
                certificationList.add(cert);
            }

            this.setCandidateCertifications(certificationList);
        }
    }

    public List<CandidateFields> getCandidateFields() {
        return fields;
    }

    public void setCandidateFields(List<CandidateFields> fields) {
        this.fields = fields;
    }

    public void updateFieldsByList(ArrayList<Integer> list, String current) {
        if (list != null && !list.isEmpty()) {
            List<CandidateFields> fieldList =
                    fields == null || fields.isEmpty() ? new ArrayList<>() : fields;

            for (int fieldID : list) {
                CandidateFields field = new CandidateFields();
                field.setCandidateID(candidateID);
                field.setFieldID(fieldID);
                field.setIsCurrent(current == null || current.contains("es") ? "Yes" : "No");
                fieldList.add(field);
            }

            this.setCandidateFields(fieldList);
        }
    }
}
