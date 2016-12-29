package co.workamerica.entities.logs.clients;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: ProfileViewLogs
 *
 */
@Entity
@Table(name="ProfileViewLogs",  schema = "workamericadb")

public class ProfileViewLogs implements Serializable {

	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ProfileViewLogsID")
	private int profileViewLogsID;
	@Column(name="ClientLoginLogsID")
	private int clientLoginLogsID;
	@Column(name="CandidateID")
	private int candidateID;
	@Column(name="Time")
	private String time;
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "ClientLoginLogsID", referencedColumnName = "ClientLoginLogsID", insertable = false, updatable = false)
	private ClientLoginLogs loginLog;

	public ProfileViewLogs() {
		super();
	}   
	public int getProfileViewLogsID() {
		return this.profileViewLogsID;
	}

	public void setProfileViewLogsID(int profileViewLogsID) {
		this.profileViewLogsID = profileViewLogsID;
	}   
	public int getClientLoginLogsID() {
		return this.clientLoginLogsID;
	}

	public void setClientLoginLogsID(int clientLoginLogsID) {
		this.clientLoginLogsID = clientLoginLogsID;
	}   
	public int getCandidateID() {
		return this.candidateID;
	}

	public void setCandidateID(int candidateID) {
		this.candidateID = candidateID;
	}   
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public ClientLoginLogs getClientLoginLog () {
		return loginLog;
	}
	
	public void setClientLoginLog (ClientLoginLogs loginLog) {
		this.loginLog = loginLog;
	}
   
}
