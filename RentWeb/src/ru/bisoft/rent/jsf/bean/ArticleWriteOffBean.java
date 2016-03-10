package ru.bisoft.rent.jsf.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import ru.bisoft.rent.model.Article;
import ru.bisoft.rent.model.Article.ArticleState;

public class ArticleWriteOffBean extends ArticleBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ArticleWriteOffBean() {
		super();
	}

	public List<Article> complete(String query)
	{
		Map<String, Object> filters = new HashMap<String, Object> ();
		filters.put("inventoryNumberArticle", query);
		//filters.put("nameArticle", query);
		return articleEJB.find(loginBean.gettUser().getEmployee().getOrganization(), ArticleState.IN_STOCK, 0, 10, filters);
	}
	
	@Override
	public List<Article> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return articleEJB.find(loginBean.gettUser().getEmployee().getOrganization(), ArticleState.WRITE_OFF, first, pageSize, filters);
	}
	
	@Override
	public int getRowCount() {
		return articleEJB.getCountWriteOff(loginBean.gettUser().getEmployee().getOrganization()).intValue();
	}
	
	@Override
	public Object getRowKey(Article object) {
		return Integer.valueOf(object.getKeyArticle());
	}
	
	@Override
	public Article getRowData(String rowKey) {
		return articleEJB.findById(Integer.valueOf(rowKey));
	}
}
