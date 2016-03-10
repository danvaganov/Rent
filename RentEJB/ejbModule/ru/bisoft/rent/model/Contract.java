package ru.bisoft.rent.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "CONTRACT")
@SequenceGenerator(name = "contract_seq", sequenceName = "contract_seq")
@NamedQueries({ 
	@NamedQuery(name = "Contract.findByOrganization", query = "SELECT c FROM Contract c WHERE c.organization = :organization"), 
	@NamedQuery(name = "Contract.findAll", query = "SELECT c FROM Contract c"),
	@NamedQuery(name = "Contract.getCount", query = "SELECT COUNT(c) FROM Contract c"),
	@NamedQuery(name = "Contract.getCountByOrganization", query = "SELECT COUNT(c) FROM Contract c WHERE c.organization = :organization"),
})

@NamedNativeQuery(name = "Contract.getNumberContract", query = "SELECT coalesce(MAX(CAST(NUMBER_CONTRACT AS Integer)), 0)+1 FROM CONTRACT WHERE CONTRACT.KEY_ORGANIZATION = ?KEY_ORGANIZATION")
public class Contract implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "contract_seq")
	@Column(name = "KEY_CONTRACT")
	private int keyContract;
	
	@Column(name = "NUMBER_CONTRACT")
	private String numberContract;
	
	@Column(name = "CREATE_CONTRACT")
	@Temporal(DATE)
	private Date createContract;
	
	@Column(name = "BEGIN_CONTRACT")
	@Temporal(DATE)
	private Date beginContract;
	
	@Column(name = "END_CONTRACT")
	@Temporal(DATE)
	private Date endContract;
	
	@Column(name = "CLOSED_CONTRACT")
	private boolean closedContract;
	
	@Column(name = "CLOSE_CONTRACT")
	@Temporal(DATE)
	private Date closeContract;
	
	@ManyToOne
	@JoinColumn(name = "KEY_PERSON", referencedColumnName = "KEY_PERSON")
	private Person person;
	
	@Column(name = "STATE_CONTRACT")
	private ContractState stateContract;
	
	@OneToMany(mappedBy = "contract", cascade = ALL)
	private List<ContractArticle> contractArticles;
	
	@ManyToOne
	@JoinColumn(name = "KEY_ORGANIZATION", referencedColumnName = "KEY_ORGANIZATION")
	private Organization organization;

	public Contract() {
		super();
	}

	public Contract(String numberContract) {
		super();
		this.numberContract = numberContract;
	}

	public int getKeyContract() {
		return keyContract;
	}

	public void setKeyContract(int keyContract) {
		this.keyContract = keyContract;
	}

	public String getNumberContract() {
		return numberContract;
	}

	public void setNumberContract(String numberContract) {
		this.numberContract = numberContract;
	}

	public Date getCreateContract() {
		return createContract;
	}

	public void setCreateContract(Date createContract) {
		this.createContract = createContract;
	}

	public Date getBeginContract() {
		return beginContract;
	}

	public void setBeginContract(Date beginContract) {
		this.beginContract = beginContract;
	}

	public Date getEndContract() {
		return endContract;
	}

	public void setEndContract(Date endContract) {
		this.endContract = endContract;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<ContractArticle> getContractArticles() {
		return contractArticles;
	}

	public void setContractArticles(List<ContractArticle> contractArticles) {
		this.contractArticles = contractArticles;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public boolean getClosedContract() {
		return closedContract;
	}

	public void setClosedContract(boolean closedContract) {
		this.closedContract = closedContract;
	}
	
	public void removeContractArticle (ContractArticle contractArticle)
	{
		contractArticles.remove(contractArticle);
	}
	public enum ContractState
	{
		OPEN("Открыт"), 
		PROCESS("В работе"),
		CLOSE("Закрыт"), 
		OUT_OF_DATE("Просрочен");
		
		private String label;
		private ContractState(String label)
		{
			this.label = label;
		}
		public String getLabel() {
			return label;
		}
	}
	public ContractState getStateContract() {
		return stateContract;
	}

	public void setStateContract(ContractState stateContract) {
		this.stateContract = stateContract;
	}

	public Date getCloseContract() {
		closeContract = (stateContract == ContractState.PROCESS)||(stateContract == ContractState.OUT_OF_DATE)?new Date():closeContract;
		return closeContract;
	}

	public void setCloseContract(Date closeContract) {
		this.closeContract = closeContract;
	}

	@Override
	public String toString() {
		return String.format("Номер %s. Создан: %tF.с %tF по %tF", numberContract, createContract, beginContract, endContract);
	}


}
