package co.workamerica.entities.criteria;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity implementation class for Entity: States
 *
 */
@Entity
@Table(name = "States",  schema = "workamericadb")
public class States implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="StateID")
	private int stateID;
	@Column(name="Name")
	private String name;
	@Column(name="Abbreviation")
	private String abbreviation;

	public States() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			States state;
			if (obj == null || (state = ((States) obj)).getStateID() <= 0) {
				return false;
			} else {
				return (this.getStateID() == state.getStateID());
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getStateID());
	}

	public int getStateID() {
		return stateID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
}
