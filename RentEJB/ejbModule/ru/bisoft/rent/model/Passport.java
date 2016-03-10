package ru.bisoft.rent.model;

import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Column;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;

@Embeddable
public class Passport {
	@Column(name = "SERIAL_PASSPORT")
	private String serialPassport;
	@Column(name = "NUMBER_PASSPORT")
	private String numberPassport;
	@Column(name = "ISSUE_PASSPORT")
	private String issuePassport;
	@Column(name = "ISSUE_DATE_PASSPORT")
	@Temporal(DATE)
	private Date issueDatePassport;
	@Column(name = "REGISTRATION_POSSPORT")
	private String registrationPassport;
	
	public String getSerialPassport() {
		return serialPassport;
	}
	public void setSerialPassport(String serialPassport) {
		this.serialPassport = serialPassport;
	}
	public String getNumberPassport() {
		return numberPassport;
	}
	public void setNumberPassport(String numberPassport) {
		this.numberPassport = numberPassport;
	}
	public String getIssuePassport() {
		return issuePassport;
	}
	public void setIssuePassport(String issuePassport) {
		this.issuePassport = issuePassport;
	}
	public Date getIssueDatePassport() {
		return issueDatePassport;
	}
	public void setIssueDatePassport(Date issueDatePassport) {
		this.issueDatePassport = issueDatePassport;
	}
	public String getRegistrationPassport() {
		return registrationPassport;
	}
	public void setRegistrationPassport(String registrationPassport) {
		this.registrationPassport = registrationPassport;
	}
}
