package ru.bisoft.rent.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ARTICLE")
@NamedQueries({ 
		@NamedQuery(name = "Article.getCountByOrganization", query = "SELECT COUNT(a) FROM Article a WHERE a.organization = :organization"),
		@NamedQuery(name = "Article.getCountByOrganizationState", query = "SELECT COUNT(a) FROM Article a WHERE a.organization = :organization AND a.stateArticle = :stateArticle"),
		@NamedQuery(name = "Article.getCountByOrganizationCategory", query = "SELECT COUNT(a) FROM Article a WHERE a.organization = :organization AND a.articleCategory = :articleCategory"),
		@NamedQuery(name = "Article.findByOrganization", query = "SELECT a FROM Article a WHERE a.organization = :organization"),
		@NamedQuery(name = "Article.findByOrganizationState", query = "SELECT a FROM Article a WHERE a.organization = :organization AND a.stateArticle = :stateArticle"),
		@NamedQuery(name = "Article.findByOrganizationCategory", query = "SELECT a FROM Article a WHERE a.organization = :organization AND a.articleCategory = :articleCategory"),

		@NamedQuery(name = "Article.findByOrganizationOutOfDate", query = "SELECT DISTINCT a FROM Article a LEFT JOIN a.contractArticles ca JOIN ca.contract c WHERE a.organization = :organization AND c.endContract < CURRENT_TIMESTAMP AND c.closedContract = false"), })
public class Article {

	@Id
	@SequenceGenerator(name = "article_seq", sequenceName = "article_seq", allocationSize = 1)
	@GeneratedValue(generator = "article_seq", strategy = SEQUENCE)
	@Column(name = "KEY_ARTICLE")
	private int keyArticle;

	@ManyToOne
	@JoinColumn(name = "KEY_ORGANIZATION", referencedColumnName = "KEY_ORGANIZATION")
	private Organization organization;

	@Column(name = "NAME_ARTICLE")
	private String nameArticle;

	@Column(name = "INVENTORY_NUMBER_ARTICLE")
	private String inventoryNumberArticle;

	@Column(name = "COST_ARTICLE")
	private String costArticle;

	@Column(name = "COST_DATE_ARTICLE")
	@Temporal(TemporalType.DATE)
	private Date costDateArticle;

	@Column(name = "LIFETIME_ARTICLE")
	private String lifetimeArticle;

	@Column(name = "RATE")
	private int rate;
	
	@Column(name = "STATE_ARTICLE")
	private ArticleState stateArticle;

	@ManyToOne
	@JoinColumn(name = "KEY_ARTICLE_CATEGORY", referencedColumnName = "KEY_ARTICLE_CATEGORY")
	private ArticleCategory articleCategory;

	@OneToMany(mappedBy = "article")
	@OrderBy("keyContractArticle desc")
	private List<ContractArticle> contractArticles;

	public Article() {
		super();
	}

	public int getKeyArticle() {
		return keyArticle;
	}

	public void setKeyArticle(int keyArticle) {
		this.keyArticle = keyArticle;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getNameArticle() {
		return nameArticle;
	}

	public void setNameArticle(String nameArticle) {
		this.nameArticle = nameArticle;
	}

	public String getInventoryNumberArticle() {
		return inventoryNumberArticle;
	}

	public void setInventoryNumberArticle(String inventoryNumberArticle) {
		this.inventoryNumberArticle = inventoryNumberArticle;
	}

	public String getCostArticle() {
		return costArticle;
	}

	public void setCostArticle(String costArticle) {
		this.costArticle = costArticle;
	}

	public Date getCostDateArticle() {
		return costDateArticle;
	}

	public void setCostDateArticle(Date costDateArticle) {
		this.costDateArticle = costDateArticle;
	}

	public String getLifetimeArticle() {
		return lifetimeArticle;
	}

	public void setLifetimeArticle(String lifetimeArticle) {
		this.lifetimeArticle = lifetimeArticle;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public List<ContractArticle> getContractArticles() {
		return contractArticles;
	}

	public void setContractArticles(List<ContractArticle> contractArticles) {
		this.contractArticles = contractArticles;
	}

	public ArticleState getStateArticle() {
		return stateArticle;
	}

	public void setStateArticle(ArticleState stateArticle) {
		this.stateArticle = stateArticle;
	}

	/*
	 * IN_STOCK - на складе 
	 * IN_RENT - арендован 
	 * IN_REPAIR - в ремонте 
	 * WRITE_OFF - списан
	 */
	public enum ArticleState{
		IN_STOCK("На складе"), 
		IN_RENT("В прокате"),
		IN_REPAIR("В ремонте"), 
		WRITE_OFF("Списан"),
		RESERVE("Резерв");
		
		private String label;
		private ArticleState(String label)
		{
			this.label = label;
		}
		public String getLabel() {
			return label;
		}
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", inventoryNumberArticle, nameArticle);
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public void incRate()
	{
		this.rate++;
	}
}
