package br.com.blankprojectsf.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the "TB_USER" database table.
 * 
 */
@Entity
@Table(name="\"TB_USER\"")
@NamedQuery(name="User.findAll", query="SELECT t FROM User t")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"ID\"")
	private String id;

	@Column(name="\"FIRST_NAME\"")
	private String firstName;

	@Column(name="\"LAST_NAME\"")
	private String lastName;

	@Column(name="\"PASSWORD\"")
	private String password;
	
	@Transient
	private String password2;

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public User() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}