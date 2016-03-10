package ru.bisoft.rent.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Table(name = "EMPLOYEE")
@SequenceGenerator(name = "employee_seq", allocationSize = 1, sequenceName = "employee_seq")
public class Employee {

	@Column(name = "KEY_EMPLOYEE")
	@Id
	@GeneratedValue(generator = "employee_seq")
	private int keyEmployee;
	
	@ManyToOne
	@JoinColumn(name = "KEY_ORGANIZATION", referencedColumnName = "KEY_ORGANIZATION")
	private Organization organization;
	
	@Column(name = "NAME_EMPLOYEE")
	private String nameEmployee;
	
	@Column(name = "SURNAME_EMPLOYEE")
	private String surnameEmployee;
	
	@Column(name = "PATRONYMIC_EMPLOYEE")
	private String patronymicEmployee;
	
	@OneToOne(mappedBy = "employee")
	private TUser tUsers;

	public int getKeyEmployee() {
		return keyEmployee;
	}

	public void setKeyEmployee(int keyEmployee) {
		this.keyEmployee = keyEmployee;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public TUser gettUsers() {
		return tUsers;
	}

	public void settUsers(TUser tUsers) {
		this.tUsers = tUsers;
	}

	public String getNameEmployee() {
		return nameEmployee;
	}

	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}

	public String getSurnameEmployee() {
		return surnameEmployee;
	}

	public void setSurnameEmployee(String surnameEmployee) {
		this.surnameEmployee = surnameEmployee;
	}

	public String getPatronymicEmployee() {
		return patronymicEmployee;
	}

	public void setPatronymicEmployee(String patronymicEmployee) {
		this.patronymicEmployee = patronymicEmployee;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", this.surnameEmployee, this.nameEmployee, this.patronymicEmployee);
	}
}
