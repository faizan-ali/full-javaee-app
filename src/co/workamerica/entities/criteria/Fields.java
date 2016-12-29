package co.workamerica.entities.criteria;


import co.workamerica.entities.candidates.CandidateFields;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity implementation class for Entity: Fields
 *
 */
@Entity
@Table(name = "Fields",  schema = "workamericadb")
public class Fields implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="FieldID")
	private int fieldID;
	@Column(name="Name")
	private String name;
	@Column(name = "Category")
	private String category;
	@OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
	private List<CandidateFields> candidateFields = new ArrayList<>();

	public Fields() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Fields field;
			if (obj == null || (field = ((Fields) obj)).getFieldID() <= 0) {
				return false;
			} else {
				return (this.getFieldID() == field.getFieldID());
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getFieldID());
	}
	
	public Fields (String name) {
		this.name = name;
	}

	public int getFieldID() {
		return fieldID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
