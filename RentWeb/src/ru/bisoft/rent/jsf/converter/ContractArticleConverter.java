package ru.bisoft.rent.jsf.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import ru.bisoft.rent.ejb.dao.ContractArticleEJB;
import ru.bisoft.rent.model.ContractArticle;

public class ContractArticleConverter implements Converter{

	@EJB
	ContractArticleEJB contractArticleEJB;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2;
		//return contractArticleEJB.findById(Integer.valueOf(arg2));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return String.valueOf(((ContractArticle) arg2).getKeyContractArticle());
	}
}
