package co.workamerica.entities.clients;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.companies.Companies;
import co.workamerica.entities.logs.clients.ClientLoginLogs;
import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.entities.texts.Conversations;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Entity implementation class for table: Clients
 *
 * @author Faizan Ali
 */

@Entity
@Table(name = "Clients", schema = "workamericadb")
@XmlRootElement
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jsonid")
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClientID")
    private int clientID;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;
    @Column(name = "Salt")
    private String salt;
    @Column(name = "ProfilesViewed")
    private int profilesViewed;
    @Column(name = "ProfilesViewedThisMonth")
    private int profilesViewedThisMonth;
    @Column(name = "ViewLimit")
    private int viewLimit;
    @Column(name = "GeoLimit")
    private String geoLimit;
    @Column(name = "SchoolAccountID")
    private int schoolAccountID;
    @Column(name = "DateCreated")
    private String dateCreated;
    @Column(name = "AssignedNumber")
    private String assignedNumber;
    @Column(name = "Approved")
    private String approved;
    @Column(name = "Company")
    private String companyName;
    @Column(name = "CompanyID")
    private int companyID;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    @JsonManagedReference
    private Set<ClientPipelines> pipeline;
    @OneToMany
    @JoinTable(name = "ClientPipelines", joinColumns = @JoinColumn(name = "ClientID"), inverseJoinColumns = @JoinColumn(name = "CandidateID", insertable = false, updatable = false))
    private List<Candidates> candidates;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<ClientLoginLogs> loginLog;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<ProfileViews> profileViews = new ArrayList<ProfileViews>();
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Conversations> conversations = new ArrayList<Conversations>();
    @ManyToOne
    @JoinColumn(name = "SchoolAccountID", referencedColumnName = "SchoolAccountID", insertable = false, updatable = false)
    private SchoolAccounts schoolAccount;
    @ManyToOne
    @JoinColumn(name = "CompanyID", referencedColumnName = "CompanyID", insertable = false, updatable = false)
    private Companies company;

    public Clients() {

    }

    @Override
    public boolean equals(Object obj) {
        try {
            Clients client;
            if (obj == null || (client = ((Clients) obj)).getClientID() <= 0) {
                return false;
            } else {
                return (this.clientID == client.getClientID());
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.clientID);
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Candidates> getCandidates() {
        return candidates;
    }

    public Set<ClientPipelines> getPipeline() {
        return pipeline;
    }

    public List<ClientLoginLogs> getLoginLog() {
        return loginLog;
    }

    public List<Conversations> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversations> conversations) {
        this.conversations = conversations;
    }

    // Adds to current pipeline, returns false if candidate already exists in
    // pipeline.

    public boolean existsInPipelineByCandidateID(int candidateID) {
        if (this.pipeline != null && !this.pipeline.isEmpty()) {
            for (ClientPipelines elt : this.pipeline) {
                if (elt.getCandidateID() == candidateID) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addToClientPipeline(ClientPipelines elt) {

        for (ClientPipelines p : pipeline) {
            if (p.getCandidateID() == elt.getCandidateID()) {
                return true;
            }
        }

        this.pipeline.add(elt);
        elt.setClient(this);
        return false;
    }

    // Removes from current pipeline, returns false if candidate does not exist
    // in pipeline.
    public boolean removeFromClientPipeline(ClientPipelines elt) {

        if (this.pipeline.remove(elt)) {
            elt.setClient(null);
            return true;
        }
        return false;
    }

    public ClientPipelines getClientPipelineByCandidateID(int id) {
        if (pipeline != null && !pipeline.isEmpty()) {
            for (ClientPipelines elt : pipeline) {
                if (elt.getCandidateID() == id && elt.getClientID() == this.getClientID()) {
                    return elt;
                }
            }
        }
        return null;
    }

    public boolean pipelineContainsCandidates(Candidates candidate) {

        for (ClientPipelines elt : pipeline) {
            if (elt.getCandidateID() == candidate.getCandidateID()) {
                return true;
            }
        }
        return false;
    }

    public boolean profileViewsExists(int candidateID) {
        if (profileViews != null || profileViews.size() != 0) {
            for (ProfileViews elt : profileViews) {
                if (elt.getCandidateID() == candidateID) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addToProfileViews(ProfileViews view) {
        this.profileViews.add(view);
    }

    public List<ProfileViews> getProfileViews() {
        if (profileViews == null) {
            return new ArrayList<ProfileViews>();
        }
        return profileViews;
    }

    public void setProfileViews(List<ProfileViews> profileViews) {
        this.profileViews = profileViews;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getProfilesViewed() {
        return profilesViewed;
    }

    public int getProfilesViewedThisMonth() {
        return profilesViewedThisMonth;
    }

    public void setProfilesViewedThisMonth(int profilesViewedThisMonth) {
        this.profilesViewedThisMonth = profilesViewedThisMonth;
    }

    public void setProfilesViewed(int profilesViewed) {
        this.profilesViewed = profilesViewed;
    }

    public int getViewLimit() {
        return viewLimit;
    }

    public void setViewLimit(int viewLimit) {
        this.viewLimit = viewLimit;
    }

    public String getGeoLimit() {
        return geoLimit;
    }

    public void setGeoLimit(String geoLimit) {
        this.geoLimit = geoLimit;
    }

    public String getLastLoginDate() {

        if (this.getLoginLog() != null && !this.getLoginLog().isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

                Date date = dateFormat.parse(this.getLoginLog().get(0).getDate());

                for (ClientLoginLogs log : this.getLoginLog()) {
                    Date temp = dateFormat.parse(log.getDate());

                    if (temp.after(date)) {
                        date = temp;
                    }
                }
                return dateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    public ClientLoginLogs getLastLoginLog() {

        if (this.getLoginLog() != null && !this.getLoginLog().isEmpty()) {
            String lastDate = getLastLoginDate();

            if (!lastDate.isEmpty()) {
                for (ClientLoginLogs loginLog : this.loginLog) {
                    if (loginLog.getDate().equals(lastDate)) {
                        return loginLog;
                    }
                }
            }
        }
        return null;
    }

    public int getSchoolAccountID() {
        return schoolAccountID;
    }

    public void setSchoolAccountID(int schoolAccountID) {
        this.schoolAccountID = schoolAccountID;
    }

    public SchoolAccounts getSchoolAccount() {
        return schoolAccount;
    }

    public void setSchoolAccount(SchoolAccounts schoolAccount) {
        this.schoolAccount = schoolAccount;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAssignedNumber() {
        return assignedNumber;
    }

    public void setAssignedNumber(String assignedNumber) {
        this.assignedNumber = assignedNumber;
    }

    public Conversations getConversationByID(int conversationID) {
        return conversations.stream().filter(x -> x.getConversationID() == conversationID).findFirst().orElse(null);
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public Companies getCompany() {
        return company;
    }

    public void setCompany(Companies company) {
        this.company = company;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
