package co.workamerica.entities.criteria;


import co.workamerica.entities.candidates.CandidateCertifications;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity implementation class for Entity: Certifications
 *
 */
@Entity
@Table(name = "Certifications",  schema = "workamericadb")
public class Certifications implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	// Use auto-incremented value of MySQL table
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CertificationID")
	private int certificationID;
	@Column(name="Name")
	private String certification;
	@OneToMany(mappedBy = "certification", fetch = FetchType.LAZY)
	private List<CandidateCertifications> candidateCertifications = new ArrayList<>();

	public Certifications() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Certifications cert;
			if (obj == null || (cert = ((Certifications) obj)).getCertificationID() <= 0) {
				return false;
			} else {
				return (this.getCertificationID() == cert.getCertificationID());
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getCertificationID());
	}
	
	public Certifications (String certification) {
		this.certification = certification;
	}

	public int getCertificationID() {
		return certificationID;
	}

	public String getCertification() {
		return certification;
	}

	public void setName(String certification) {
		this.certification = certification;
	}

    public List<CandidateCertifications> getCandidateCertifications() {
        return candidateCertifications;
    }

    public void setCandidateCertifications(List<CandidateCertifications> candidateCertifications) {
        this.candidateCertifications = candidateCertifications;
    }
}
