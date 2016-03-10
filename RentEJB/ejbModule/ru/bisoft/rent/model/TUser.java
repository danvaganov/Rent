package ru.bisoft.rent.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TUser
 *
 */
@Entity
@Table(name="TUSER")
@SequenceGenerator(name = "tuser_seq", sequenceName = "tuser_seq")
@NamedQueries({ 
	@NamedQuery(name = "TUser.findByID", query = "SELECT u FROM TUser u WHERE u.userIDTUser = :userIDTUser"), 
	@NamedQuery(name = "TUser.getCount", query = "SELECT COUNT(u) FROM TUser u") 
})
public class TUser implements Serializable {

	@Id
	@Column(name = "USERID_TUSER")
	private String userIDTUser;
	
	@Column(name = "PASSWORD_TUSER")
	private String passwordTUser;
	
	@OneToOne
	@JoinColumn(name = "KEY_EMPLOYEE", referencedColumnName = "KEY_EMPLOYEE")
	private Employee employee;
	
	private static final long serialVersionUID = 1L;

	public TUser() {
		super();
	}

	public String getUserIDTUser() {
		return userIDTUser;
	}

	public void setUserIDTUser(String userIDTUser) {
		this.userIDTUser = userIDTUser;
	}

	public String getPasswordTUser() {
		return passwordTUser;
	}

	public void setPasswordTUser(String passwordTUser) {
		this.passwordTUser = passwordTUser;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}   
}
