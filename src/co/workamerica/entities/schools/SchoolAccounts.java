package co.workamerica.entities.schools;

import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.companies.Companies;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.logs.schools.SchoolLoginLogs;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Entity implementation class for Entity: SchoolAccounts
 *
 */
@Entity
@Table(name = "SchoolAccounts",  schema = "workamericadb")
public class SchoolAccounts implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public SchoolAccounts() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "SchoolAccountID")
	private int schoolAccountID;
	@Column (name = "Email")
	private String email;
	@Column (name = "Password")
	private String password;
	@Column (name = "Salt")
	private String salt;
	@Column (name = "SchoolID")
	private int schoolID;
	@Column (name = "EmployerCode")
	private String employerCode;
	@ManyToOne
	@JoinColumn(name = "SchoolID", referencedColumnName = "SchoolID", insertable = false, updatable = false)
	private Schools school;
	@OneToMany(mappedBy = "schoolAccount", fetch = FetchType.EAGER)
	private List<Clients> clients = new ArrayList<Clients>();
	@OneToMany(mappedBy = "schoolAccount", fetch = FetchType.EAGER)
	private List<Companies> companies = new ArrayList<Companies>();
    @OneToMany(mappedBy = "schoolAccount", fetch = FetchType.EAGER)
    private List<SchoolLoginLogs> loginLog;
	
	@Override
	public boolean equals(Object obj) {
		try {
			SchoolAccounts schoolAccount;
			if (obj == null || (schoolAccount = ((SchoolAccounts) obj)).getSchoolAccountID() <= 0) {
				return false;
			} else {
				return (this.getSchoolAccountID() == schoolAccount.getSchoolAccountID());
			}
		} catch (Exception e) {
			return false;
		}
	}

    @Override

	public int hashCode() {
		return Objects.hash(this.getSchoolAccountID());
	}
	
	public int getSchoolAccountID() {
		return schoolAccountID;
	}
	public void setSchoolAccountID(int schoolAccountID) {
		this.schoolAccountID = schoolAccountID;
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
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Schools getSchool() {
		return school;
	}
	public void setSchool(Schools school) {
		this.school = school;
	}
	public int getSchoolID() {
		return schoolID;
	}
	public void setSchoolID (int schoolID) {
		this.schoolID = schoolID;
	}

	public String getEmployerCode() {
		return employerCode;
	}

	public void setEmployerCode(String employerCode) {
		this.employerCode = employerCode;
	}

	public List<Clients> getClients() {
		return clients;
	}

	public void setClients(List<Clients> clients) {
		this.clients = clients;
	}

    public List<SchoolLoginLogs> getLoginLog() {
        return loginLog;
    }

    public void setLoginLog(List<SchoolLoginLogs> loginLog) {
        this.loginLog = loginLog;
    }

    public String getLastLoginDate() {

        if (this.getLoginLog() != null && !this.getLoginLog().isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

                Date date = dateFormat.parse(this.getLoginLog().get(0).getDate());

                for (SchoolLoginLogs log : this.getLoginLog()) {
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

	public List<Companies> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Companies> companies) {
		this.companies = companies;
	}
}
