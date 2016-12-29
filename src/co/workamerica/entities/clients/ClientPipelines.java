package co.workamerica.entities.clients;

import co.workamerica.entities.candidates.Candidates;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Entity implementation class for join table: ClientPipelines
 * 
 * This entity stores the pipeline of every Client.
 * 
 * @author Faizan Ali
 *
 */

@Entity
@Table(name = "ClientPipelines",  schema = "workamericadb")
@XmlRootElement
public class ClientPipelines implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ClientPipelineID")
	private int clientPipelineID;
	@Column(name = "ClientID")
	private int clientID;
	@Column(name = "CandidateID")
	private int candidateID;
	@Column(name = "Rating")
	private String rating;
	@Column(name = "Status")
	private String status;
	@Column(name = "DateAdded")
	private String dateAdded;
	@Column(name = "Notes")
	private String notes;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ClientID", referencedColumnName = "ClientID", insertable = false, updatable = false)
	@JsonBackReference
	private Clients client;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CandidateID", referencedColumnName = "CandidateID", insertable = false, updatable = false)
	@JsonBackReference
	private Candidates candidate;

	public ClientPipelines() {
		this.rating = null;
		this.status = null;
		this.dateAdded = null;
	}

	public ClientPipelines(int clientID, int candidateID, String rating, String status, String dateAdded) {
		this.clientID = clientID;
		this.candidateID = candidateID;
		this.rating = rating;
		this.status = status;
		this.dateAdded = dateAdded;
	}

	public int getClientPipelineID() {
		return clientPipelineID;
	}

	public void setClientPipelineID(int clientPipelineID) {
		this.clientPipelineID = clientPipelineID;
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

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Clients getClient() {
		return client;
	}

	public void setClient(Clients client) {
		this.client = client;
	}

	public Candidates getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidates candidate) {
		this.candidate = candidate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
