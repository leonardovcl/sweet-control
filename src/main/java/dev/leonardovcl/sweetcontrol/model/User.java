package dev.leonardovcl.sweetcontrol.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	
	@NotEmpty(message = "Must not be empty!")
	@Column(name = "user_name", unique=true)
	private String username;
	
	@NotEmpty(message = "Must not be empty!")
	@Column(name = "password")
	private String password;
	
	public User() {
		
	}

	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
	
	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder();
		objString.append("[User ").append("#").append(this.getId()).append("] ");
		objString.append(this.getUsername());
		
		objString.append(" [@");
		objString.append(Integer.toHexString(System.identityHashCode(this)));
		objString.append("]");
				
		return objString.toString();
	}

}
