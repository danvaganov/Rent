package ru.bisoft.rent.model;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CONTRACT_ARTICLE")
@SequenceGenerator(name = "contract_article_seq", sequenceName = "contract_article_seq")
public class ContractArticle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "contract_article_seq")
	@Column(name = "KEY_CONTRACT_ARTICLE")
	private int keyContractArticle;
	
	@ManyToOne
	@JoinColumn(name = "KEY_CONTRACT", referencedColumnName = "KEY_CONTRACT")
	private Contract contract;
	
	@ManyToOne(cascade = { MERGE, PERSIST })
	@JoinColumn(name = "KEY_ARTICLE", referencedColumnName = "KEY_ARTICLE")
	private Article article;

	public int getKeyContractArticle() {
		return keyContractArticle;
	}

	public void setKeyContractArticle(int keyContractArticle) {
		this.keyContractArticle = keyContractArticle;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + keyContractArticle;
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
		ContractArticle other = (ContractArticle) obj;
		if (keyContractArticle != other.keyContractArticle)
			return false;
		return true;
	}
	
}
