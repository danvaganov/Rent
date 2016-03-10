package ru.bisoft.rent.model;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import ru.bisoft.rent.model.desc.Description;

/**
 * Entity implementation class for Entity: Person
 *
 */

/**
 * The persistent class for the PERSON database table.
 * 
 */
@Entity
@Table(name="PERSON")
@NamedQueries({
	@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p order by p.keyPerson"),
	@NamedQuery(name="Person.findByOrganization", query="SELECT p FROM Person p JOIN p.personOrganizations po WHERE po.organization = :organization"),
	@NamedQuery(name="Person.findByPassport", query="SELECT p FROM Person p WHERE p.passport.serialPassport = :serialPassport AND p.passport.numberPassport = :numberPassport AND p.passport.issueDatePassport = :issueDatePassport"),
	@NamedQuery(name="Person.getCount", query="SELECT COUNT(p) FROM Person p"),
	@NamedQuery(name="Person.getCountByOrganization", query="SELECT COUNT(p) FROM Person p JOIN p.personOrganizations po WHERE po.organization = :organization")
})
@XmlRootElement
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name="BIRTHDAY_PERSON")
	@Description(value = "День рождения")
	private Date birthdayPerson;

	@Column(name = "CREATE_PERSON")
	@Temporal(TIMESTAMP)
	private Date createPerson;

	@Column(name="EMAIL_PERSON")
	@Description(value = "Почтовый ящик")
	private String emailPerson;

	@Id
	@SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="person_seq")
	@Column(name="KEY_PERSON")
	private int keyPerson;

	@Column(name="NAME_PERSON", nullable=false)
	@Description(value = "Имя")
	private String namePerson;
	
	@Column(name="PATRONYMIC_PERSON")
	@Description(value = "Отчество")
	private String patronymicPerson;

	@Column(name="PHONE_PERSON")
	@Description(value = "Телефон")
	private String phonePerson;
	
	@Column(name="SURNAME_PERSON", nullable=false)
	@Description(value = "Фамилия")
	private String surnamePerson;
	
	@Column(name = "DWELLING_PERSON")
	private String dwellingPerson;
	
	@Embedded
	private Passport passport;
	
	@OneToMany(mappedBy = "person")
	private List<PersonOrganization> personOrganizations;
	
	public Person() {
	}

	public Date getBirthdayPerson() {
		return birthdayPerson;
	}

	public void setBirthdayPerson(Date birthdayPerson) {
		this.birthdayPerson = birthdayPerson;
	}

	public Date getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(Date createPerson) {
		this.createPerson = createPerson;
	}

	public String getEmailPerson() {
		return emailPerson;
	}

	public void setEmailPerson(String emailPerson) {
		this.emailPerson = emailPerson;
	}

	public int getKeyPerson() {
		return keyPerson;
	}

	public void setKeyPerson(int keyPerson) {
		this.keyPerson = keyPerson;
	}

	public String getNamePerson() {
		return namePerson;
	}

	public void setNamePerson(String namePerson) {
		this.namePerson = namePerson;
	}

	public String getPatronymicPerson() {
		return patronymicPerson;
	}

	public void setPatronymicPerson(String patronymicPerson) {
		this.patronymicPerson = patronymicPerson;
	}

	public String getPhonePerson() {
		return phonePerson;
	}

	public void setPhonePerson(String phonePerson) {
		this.phonePerson = phonePerson;
	}

	public String getSurnamePerson() {
		return surnamePerson;
	}

	public void setSurnamePerson(String surnamePerson) {
		this.surnamePerson = surnamePerson;
	}

	public List<PersonOrganization> getPersonOrganizations() {
		personOrganizations = personOrganizations == null? new ArrayList<PersonOrganization>():personOrganizations;
		return personOrganizations;
	}

	public void setPersonOrganizations(List<PersonOrganization> personOrganizations) {
		this.personOrganizations = personOrganizations;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", this.surnamePerson, this.namePerson, this.patronymicPerson).trim();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + keyPerson;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (keyPerson != other.keyPerson)
			return false;
		return true;
	}

	public Passport getPassport() {
		passport = passport == null? new Passport(): passport;
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public String getDwellingPerson() {
		return dwellingPerson;
	}

	public void setDwellingPerson(String dwellingPerson) {
		this.dwellingPerson = dwellingPerson;
	}
}