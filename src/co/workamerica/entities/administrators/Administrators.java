package co.workamerica.entities.administrators;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: Administrators
 *
 */
//Tells JPA that this class defines an entity to be stored in a database
@Entity
//Overrides default table name
@Table(name = "Administrators",  schema = "workamericadb")
public class Administrators implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AdministratorID")
	private int administratorID;
	@Column(name = "Username")
	private String username;
	@Column(name = "Password")
	private String password;
	@Column(name = "Salt")
	private String salt;

	public Administrators() {
		this.username = null;
		this.password = null;
	}

	public Administrators(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getAdministratorID() {
		return administratorID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}   
}
