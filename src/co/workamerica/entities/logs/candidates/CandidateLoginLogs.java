package co.workamerica.entities.logs.candidates;

import co.workamerica.entities.candidates.Candidates;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name="CandidateLoginLogs",  schema = "workamericadb")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jsonid")
@XmlRootElement
public class CandidateLoginLogs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CandidateLoginLogsID")
	private int candidateLoginLogsID;
	@Column(name="CandidateID")
	private int candidateID;
	@Column(name="Date")
	private String date;
	@Column(name="Time")
	private String time;
	@ManyToOne
	@JoinColumn(name = "CandidateID", referencedColumnName = "CandidateID", insertable=false, updatable=false)
	private Candidates candidate;
	@OneToMany(mappedBy = "loginLog")
	private List <CandidateActivityLogs> activityLog;

	public CandidateLoginLogs() {
		super();
	}
	
	public CandidateLoginLogs (int candidateID, String date, String time) {
		this.candidateID = candidateID;
		this.date = date;
		this.time = time;
	}

	public int getCandidateLoginLogsID() {
		return candidateLoginLogsID;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(int candidateID) {
		this.candidateID = candidateID;
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

	public Candidates getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidates candidate) {
		this.candidate = candidate;
	}

	public List<CandidateActivityLogs> getActivityLog() {
		return activityLog;
	}

	public void setActivityLog(List<CandidateActivityLogs> activityLog) {
		this.activityLog = activityLog;
	}  
}
