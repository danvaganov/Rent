package ru.bisoft.rent.jsf.bean;

import javax.ejb.EJB;

import ru.bisoft.rent.ejb.dao.OrganizationEJB;
import ru.bisoft.rent.model.Organization;

public class OrganizationBean {
	
	@EJB
	OrganizationEJB organizationEJB;
	
	LoginBean loginBean;

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void update (Organization organization)
	{
		organizationEJB.update(organization);
	}
}
