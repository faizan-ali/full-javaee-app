package co.workamerica.entities.logs.clients;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: PipelineChangeLogs
 *
 */
@Entity
@Table(name="PipelineChangeLogs",  schema = "workamericadb")

public class PipelineChangeLogs implements Serializable {

	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PipelineChangeLogsID")
	private int pipelineChangeLogsID;
	@Column(name="ClientLoginLogsID")
	private int clientLoginLogsID;
	@Column(name="CandidateID")
	private int candidateID;
	@Column(name="PipelineAction")
	private String pipelineAction;
	@Column(name="PipelineChange")
	private String pipelineChange;
	@Column(name="Time")
	private String time;
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "ClientLoginLogsID", referencedColumnName = "ClientLoginLogsID", insertable = false, updatable = false)
	private ClientLoginLogs loginLog;

	public PipelineChangeLogs() {
		super();
	}   
	public int getPipelineChangeLogsID() {
		return this.pipelineChangeLogsID;
	}

	public void setPipelineChangeLogsID(int pipelineChangeLogsID) {
		this.pipelineChangeLogsID = pipelineChangeLogsID;
	}   
	public int getClientLoginLogsID() {
		return this.clientLoginLogsID;
	}

	public void setClientLoginLogsID(int clientLoginLogsID) {
		this.clientLoginLogsID = clientLoginLogsID;
	}   
	public String getPipelineAction() {
		return this.pipelineAction;
	}

	public void setPipelineAction(String pipelineAction) {
		this.pipelineAction = pipelineAction;
	}   
	public String getPipelineChange() {
		return this.pipelineChange;
	}

	public void setPipelineChange(String pipelineChange) {
		this.pipelineChange = pipelineChange;
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
	
	public int getCandidateID() {
		return this.candidateID;
	}

	public void setCandidateID(int candidateID) {
		this.candidateID = candidateID;
	}   
}
