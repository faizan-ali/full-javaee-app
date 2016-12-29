package co.workamerica.entities.logs.schools;

import co.workamerica.entities.candidates.Candidates;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: SchoolActivityLogs
 *
 */
@Entity
@Table(name="SchoolActivityLogs",  schema = "workamericadb")
public class SchoolActivityLogs implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="SchoolActivityLogID")
	private int schoolActivityLogID;
	@Column (name="SchoolLoginLogID")
	private int schoolLoginLogID;
	@Column (name="CandidateID")
	private int candidateID;
	@Column(name="SchoolActivity")
	private String schoolActivity;
	@Column (name = "Time")
	private String time;
	@ManyToOne
	@JoinColumn(name = "SchoolLoginLogID", referencedColumnName = "SchoolLoginLogID", insertable = false, updatable = false)
	private SchoolLoginLogs loginLog;
	@ManyToOne
	@JoinColumn(name = "CandidateID", referencedColumnName = "CandidateID", insertable = false, updatable = false)
	private Candidates candidate;
	
	public SchoolActivityLogs() {
		super();
	}

	public int getSchoolActivityLogID() {
		return schoolActivityLogID;
	}

	public int getSchoolLoginLogID() {
		return schoolLoginLogID;
	}

	public void setSchoolLoginLogID(int schoolLoginLogID) {
		this.schoolLoginLogID = schoolLoginLogID;
	}

	public String getSchoolActivity() {
		return schoolActivity;
	}

	public void setSchoolActivity(String schoolActivity) {
		this.schoolActivity = schoolActivity;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public SchoolLoginLogs getLoginLog() {
		return loginLog;
	}

	public void setLoginLog(SchoolLoginLogs loginLog) {
		this.loginLog = loginLog;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(int candidateID) {
		this.candidateID = candidateID;
	}

	public Candidates getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidates candidate) {
		this.candidate = candidate;
	}
   
}
