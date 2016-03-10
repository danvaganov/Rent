package ru.bisoft.rent.jsf.bean;

import javax.ejb.EJB;

import org.primefaces.model.LazyDataModel;

import ru.bisoft.rent.ejb.dao.ArticleCategoryEJB;
import ru.bisoft.rent.ejb.dao.ArticleEJB;
import ru.bisoft.rent.model.Article;
import ru.bisoft.rent.model.Article.ArticleState;

public class ArticleBean extends LazyDataModel<Article> {
	/**
	 * 2
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	ArticleCategoryEJB articleCategoryEJB;
	@EJB
	ArticleEJB articleEJB;
	
	LoginBean loginBean;
	
	Article selection;
	
	public ArticleBean() {
		super();
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	public void updateArticle(Article article)
	{
		articleEJB.update(article);
	}
	
	public void prepareInsert()
	{
		selection = new Article();
		selection.setStateArticle(ArticleState.IN_STOCK);
		selection.setOrganization(loginBean.gettUser().getEmployee().getOrganization());
	}
	
	public ArticleState[] getStatuses()
	{
		return ArticleState.values();
	}

	public Article getSelection() {
		return selection;
	}

	public void setSelection(Article selection) {
		this.selection = selection;
	}
}
