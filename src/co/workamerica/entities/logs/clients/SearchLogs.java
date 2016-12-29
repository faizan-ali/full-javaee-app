package co.workamerica.entities.logs.clients;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: SearchLogs
 *
 */
@Entity
@Table(name="SearchLogs",  schema = "workamericadb")

public class SearchLogs implements Serializable {

	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SearchLogsID")
	private int searchLogsID;
	@Column(name="ClientLoginLogsID")
	private int clientLoginLogsID;
	@Column(name="SearchKeyword")
	private String searchKeyword;
	@Column(name="SearchCriteria")
	private String searchCriteria;
	@Column(name = "Radius")
	private String radius;
	@Column(name="Time")
	private String time;
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "ClientLoginLogsID", referencedColumnName = "ClientLoginLogsID", insertable = false, updatable = false)
	private ClientLoginLogs loginLog;

	public SearchLogs() {
		super();
	}   
	public int getSearchLogsID() {
		return this.searchLogsID;
	}

	public void setSearchLogsID(int searchLogsID) {
		this.searchLogsID = searchLogsID;
	}   
	public int getClientLoginLogsID() {
		return this.clientLoginLogsID;
	}

	public void setClientLoginLogsID(int clientLoginLogsID) {
		this.clientLoginLogsID = clientLoginLogsID;
	}   
	public String getSearchKeyword() {
		return this.searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}   
	public String getSearchCriteria() {
		return this.searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
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
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	public ClientLoginLogs getLoginLog() {
		return loginLog;
	}
   
}
