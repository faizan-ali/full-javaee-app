package co.workamerica.entities.clients;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: ProfileViews
 *
 */
@Entity
//Overrides default table name
@Table(name = "ProfileViews",  schema = "workamericadb")
public class ProfileViews implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	// Tells JPA to use auto-incremented values of the MySQL table as the userID
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProfileViewsID")
	private int profileViewsID;
	@Column(name = "ClientID")
	private int clientID;
	@Column(name = "CandidateID")
	private int candidateID; 
	@ManyToOne
	@JoinColumn(name = "ClientID", referencedColumnName = "ClientID", insertable=false, updatable=false)
	private Clients client;
	
	public ProfileViews () {
		
	}

	public int getProfileViewsID() {
		return profileViewsID;
	}

	public void setProfileViewsID(int profileViewsID) {
		this.profileViewsID = profileViewsID;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(int candidateID) {
		this.candidateID = candidateID;
	}

	public Clients getClient() {
		return client;
	}

	public void setClient(Clients client) {
		this.client = client;
	}
}
