package co.workamerica.entities.logs.clients;


import co.workamerica.entities.clients.Clients;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Entity implementation class for Entity: ClientLoginLogs
 *
 */
@Entity
@Table(name="ClientLoginLogs",  schema = "workamericadb")
public class ClientLoginLogs implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ClientLoginLogsID")
	private int clientLoginLogsID;
	@Column(name="ClientID")
	private int clientID;
	@Column(name="Date")
	private String date;
	@Column(name="Time")
	private String time;
	@ManyToOne
	@JoinColumn(name = "ClientID", referencedColumnName = "ClientID", insertable=false, updatable=false)
	private Clients client;
	@OneToMany(mappedBy = "loginLog")
	private List <PipelineChangeLogs> pipelineChangeLog;
	@OneToMany(mappedBy = "loginLog")
	private List <ProfileViewLogs> profileViewLog;
	@OneToMany(mappedBy = "loginLog")
	@OrderBy("time DESC")
	private List <SearchLogs> searchLog;
	
	public ClientLoginLogs () {
		
	}
	
	public ClientLoginLogs (int clientID, String date, String time) {
		this.clientID = clientID;
		this.date = date;
		this.time = time;
	}
	
	public int getClientLoginLogsID() {
		return this.clientLoginLogsID;
	}

	public void setClientLoginLogsID(int clientLoginLogsID) {
		this.clientLoginLogsID = clientLoginLogsID;
	}   
	public int getClientID() {
		return this.clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}   
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}   
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public Clients getClient () {
		return client;
	}
	
	public void setClient (Clients client) {
		this.client = client;
	}
	public List <SearchLogs> getSearchLog () {
		return searchLog;
	}
	
	public List <PipelineChangeLogs> getPipelineChangeLog() {
		return pipelineChangeLog;
	}
	
	public List <ProfileViewLogs> getProfileViewLog () {
		return profileViewLog;
	}
   
}
