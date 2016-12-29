package co.workamerica.entities.companies;

import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.schools.SchoolAccounts;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Faizan on 7/13/2016.
 */
@Entity
@Table(name = "Companies", schema = "workamericadb")
public class Companies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CompanyID")
    private int companyID;
    @Column(name = "Name")
    private String name;
    @Column(name = "ViewLimit")
    private int viewLimit;
    @Column(name = "ProfilesViewed")
    private int profilesViewed;
    @Column(name = "ProfilesViewedThisMonth")
    private int profilesViewedThisMonth;
    @Column(name = "GeoLimit")
    private String geoLimit;
    @Column(name = "SchoolAccountID")
    private int schoolAccountID;
    @ManyToOne
    @JoinColumn(name = "SchoolAccountID", referencedColumnName = "SchoolAccountID", insertable = false, updatable = false)
    private SchoolAccounts schoolAccount;
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Clients> clients = new ArrayList<Clients>();

    public Companies () {

    }

    @Override
    public boolean equals(Object obj) {
        try {
            Companies company;
            if (obj == null || (company = ((Companies) obj)).getCompanyID() <= 0) {
                return false;
            } else {
                return (this.companyID == company.getCompanyID());
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.companyID);
    }
    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyId) {
        this.companyID = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getViewLimit() {
        return viewLimit;
    }

    public void setViewLimit(int viewLimit) {
        this.viewLimit = viewLimit;
    }

    public int getProfilesViewed() {
        return profilesViewed;
    }

    public void setProfilesViewed(int profilesViewed) {
        this.profilesViewed = profilesViewed;
    }

    public int getProfilesViewedThisMonth() {
        return profilesViewedThisMonth;
    }

    public void setProfilesViewedThisMonth(int profilesViewedThisMonth) {
        this.profilesViewedThisMonth = profilesViewedThisMonth;
    }

    public String getGeoLimit() {
        return geoLimit;
    }

    public void setGeoLimit(String geoLimit) {
        this.geoLimit = geoLimit;
    }

    public int getSchoolAccountID() {
        return schoolAccountID;
    }

    public void setSchoolAccountID(int schoolAccountId) {
        this.schoolAccountID = schoolAccountId;
    }

    public SchoolAccounts getSchoolAccount() {
        return schoolAccount;
    }

    public void setSchoolAccount(SchoolAccounts schoolAccount) {
        this.schoolAccount = schoolAccount;
    }

    public List<Clients> getClients() {
        return clients;
    }

    public void setClients(List<Clients> clients) {
        this.clients = clients;
    }
}
