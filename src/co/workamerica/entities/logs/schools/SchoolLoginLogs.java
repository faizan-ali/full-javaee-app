package co.workamerica.entities.logs.schools;

import co.workamerica.entities.schools.SchoolAccounts;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Entity implementation class for Entity: SchoolLoginLogs
 *
 */
@Entity
@Table(name="SchoolLoginLogs",  schema = "workamericadb")
public class SchoolLoginLogs implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="SchoolLoginLogID")
	private int schoolLoginLogID;
	@Column (name="SchoolAccountID")
	private int schoolAccountID;
	@Column (name="Date")
	private String date;
	@Column (name="Time")
	private String time;
	@OneToMany(mappedBy = "loginLog")
	private List <SchoolActivityLogs> activityLog;
	@ManyToOne
	@JoinColumn(name = "SchoolAccountID", referencedColumnName = "SchoolAccountID", insertable=false, updatable=false)
	private SchoolAccounts schoolAccount;

    public SchoolLoginLogs() {
		super();
	}
	
	public SchoolLoginLogs (int schoolAccountID, String date, String time) {
		this.schoolAccountID = schoolAccountID;
		this.date = date;
		this.time = time;
	}

	public int getSchoolLoginLogID() {
		return schoolLoginLogID;
	}

	public int getSchoolAccountID() {
		return schoolAccountID;
	}

	public void setSchoolAccountID(int schoolAccountID) {
		this.schoolAccountID = schoolAccountID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<SchoolActivityLogs> getActivityLog() {
		return activityLog;
	}

	public void setActivityLog(List<SchoolActivityLogs> activityLog) {
		this.activityLog = activityLog;
	}

    public SchoolAccounts getSchoolAccount() {
        return schoolAccount;
    }

    public void setSchoolAccount(SchoolAccounts schoolAccount) {
        this.schoolAccount = schoolAccount;
    }
	
}
