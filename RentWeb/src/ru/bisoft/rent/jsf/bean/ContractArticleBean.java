package ru.bisoft.rent.jsf.bean;

import java.io.Serializable;

import javax.ejb.EJB;

import ru.bisoft.rent.ejb.dao.ContractArticleEJB;
import ru.bisoft.rent.model.ContractArticle;

public class ContractArticleBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	ContractArticleEJB contractArticleEJB;
	
	ContractBean contractBean;

	private ContractArticle selection;

	public ContractArticle getSelection() {
		return selection;
	}

	public void setSelection(ContractArticle selection) {
		this.selection = selection;
	}
	
	public void prepareInsert()
	{
		selection = new ContractArticle();
	}
	
	public void update ()
	{
		contractBean.getSelection().getContractArticles().add(selection);
		selection.getArticle().getContractArticles().add(selection);
		selection.setContract(contractBean.getSelection());
	}

	public ContractBean getContractBean() {
		return contractBean;
	}

	public void setContractBean(ContractBean contractBean) {
		this.contractBean = contractBean;
	}
}
