package ru.bisoft.rent.model;

import static javax.persistence.FetchType.LAZY;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLE_CATEGORY")
@NamedQueries({ 
	@NamedQuery(name = "ArticleCategory.findAll", query = "SELECT a FROM ArticleCategory a ORDER BY a.keyArticleCategory"), 
	@NamedQuery(name = "ArticleCategory.getCount", query = "SELECT COUNT(a) FROM ArticleCategory a") 
})
public class ArticleCategory {

	@Column(name = "KEY_ARTICLE_CATEGORY")
	@Id
	@SequenceGenerator(name = "article_category_seq", sequenceName = "article_category_seq")
	@GeneratedValue(generator = "article_category_seq")
	private int keyArticleCategory;
	
	@Column(name = "NAME_ARTICLE_CATEGORY")
	private String nameArticleCategory;
	
	@OneToMany(mappedBy="articleCategory", orphanRemoval=true)
	private List<Article> articles;
	
	@OneToMany(mappedBy="articleCategory", orphanRemoval=true, fetch = LAZY)
	private List<ArticleCategory> articleCategories;
	
	@Column(name = "CATEGORY_ARTICLE_CATEGORY")
	private String categoryArticleCategory;
	
	@ManyToOne
	@JoinColumn(name = "KEY_PARENT_ARTICLE_CATEGORY", referencedColumnName = "KEY_ARTICLE_CATEGORY")
	private ArticleCategory articleCategory;

	public ArticleCategory() {
		super();
	}

	public int getKeyArticleCategory() {
		return keyArticleCategory;
	}

	public void setKeyArticleCategory(int keyArticleCategory) {
		this.keyArticleCategory = keyArticleCategory;
	}

	public String getNameArticleCategory() {
		return nameArticleCategory;
	}

	public void setNameArticleCategory(String nameArticleCategory) {
		this.nameArticleCategory = nameArticleCategory;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<ArticleCategory> getArticleCategories() {
		return articleCategories;
	}

	public void setArticleCategories(List<ArticleCategory> articleCategories) {
		this.articleCategories = articleCategories;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + keyArticleCategory;
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
		ArticleCategory other = (ArticleCategory) obj;
		if (keyArticleCategory != other.keyArticleCategory)
			return false;
		return true;
	}

	public String getCategoryArticleCategory() {
		return categoryArticleCategory;
	}

	public void setCategoryArticleCategory(String categoryArticleCategory) {
		this.categoryArticleCategory = categoryArticleCategory;
	}
}
