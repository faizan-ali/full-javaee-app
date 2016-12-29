package co.workamerica.entities.logs.candidates;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: CandidateActivityLogs
 *
 */
@Entity
@Table(name="CandidateActivityLogs",  schema = "workamericadb")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jsonid")
@XmlRootElement
public class CandidateActivityLogs implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CandidateActivityLogsID")
	private int candidateActivityLogsID;
	@Column(name="CandidateLoginLogsID")
	private int candidateLoginLogsID;
	@Column(name="ProfileUpdates")
	private String profileUpdates;
	@Column(name="Time")
	private String time;
	@ManyToOne
	@JoinColumn(name = "CandidateLoginLogsID", referencedColumnName = "CandidateLoginLogsID", insertable = false, updatable = false)
	private CandidateLoginLogs loginLog;

	public CandidateActivityLogs() {
		super();
	}

	public int getCandidateActivityLogsID() {
		return candidateActivityLogsID;
	}

	public void setCandidateActivityLogsID(int candidateActivityLogsID) {
		this.candidateActivityLogsID = candidateActivityLogsID;
	}

	public int getCandidateLoginLogsID() {
		return candidateLoginLogsID;
	}

	public void setCandidateLoginLogsID(int candidateLoginLogsID) {
		this.candidateLoginLogsID = candidateLoginLogsID;
	}

	public String getProfileUpdates() {
		return profileUpdates;
	}

	public void setProfileUpdates(String profileUpdates) {
		this.profileUpdates = profileUpdates;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public CandidateLoginLogs getLoginLog() {
		return loginLog;
	}

	public void setLoginLog(CandidateLoginLogs loginLog) {
		this.loginLog = loginLog;
	}  
}
