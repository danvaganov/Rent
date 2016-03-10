package ru.bisoft.rent.jsf.bean;

import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ru.bisoft.rent.model.ArticleCategory;

class ArticleCategoryTreeNode extends DefaultTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArticleCategoryTreeNode() {
		super();
	}

	public ArticleCategoryTreeNode(Object data, TreeNode parent) {
		super(data, parent);
		List<TreeNode> children = getChildren();
		for (ArticleCategory articleCategory: ((ArticleCategory)data).getArticleCategories())
		{
			children.add(new ArticleCategoryTreeNode(articleCategory, this));
		}
	}

	public ArticleCategoryTreeNode(Object data) {
		super(data);
	}

	public ArticleCategoryTreeNode(String type, Object data, TreeNode parent) {
		super(type, data, parent);
	}
}
