package ru.bisoft.rent.jsf.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import org.primefaces.model.TreeNode;

import ru.bisoft.rent.ejb.dao.ArticleCategoryEJB;

public class ArticleCategoryBean {

	@EJB
	ArticleCategoryEJB articleCategoryEJB;
	
	TreeNode root;
	TreeNode selection;
	
	@PostConstruct
	private void init ()
	{
		root = new ArticleCategoryTreeNode(articleCategoryEJB.find().get(0), null);
	}
	
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelection() {
		return selection;
	}

	public void setSelection(TreeNode selection) {
		this.selection = selection;
	}
}
