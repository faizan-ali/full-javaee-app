package co.workamerica.entities.criteria;


import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.schools.SchoolAccounts;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity implementation class for Entity: Schools
 *
 */
@Entity
@Table(name = "Schools",  schema = "workamericadb")
public class Schools implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SchoolID")
	private int schoolID;
	@Column(name="Name")
	private String name;
	@Column(name = "State")
	private String state;
	@OneToMany(mappedBy = "school")
    @JsonIgnore
	private List<SchoolAccounts> schoolAccounts;
	@OneToMany(mappedBy = "schoolEntity")
	@JsonIgnore
	private List<Candidates> candidates;

	public Schools() {
		
	}
	
	public Schools (String name, String state) {
		this.name = name;
		this.state = state;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Schools school;
			if (obj == null || (school = ((Schools) obj)).getSchoolID() <= 0) {
				return false;
			} else {
				return (this.getSchoolID() == school.getSchoolID());
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getSchoolID());
	}
	
	public int getSchoolID() {
		return schoolID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getState () {
		return state;
	}
	
	public void setState (String state) {
		this.state = state;
	}

	public List<SchoolAccounts> getSchoolAccounts() {
		return schoolAccounts;
	}

	public void setSchoolAccounts(List<SchoolAccounts> schoolAccounts) {
		this.schoolAccounts = schoolAccounts;
	}
	
	public void addSchoolAccount (SchoolAccounts account) {
		if (this.getSchoolAccounts() == null) {
			this.setSchoolAccounts(new ArrayList<SchoolAccounts>());
		}
		this.getSchoolAccounts().add(account);
	}

	public List<Candidates> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidates> candidates) {
		this.candidates = candidates;
	}
}
