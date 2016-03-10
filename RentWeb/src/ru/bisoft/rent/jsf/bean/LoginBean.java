package ru.bisoft.rent.jsf.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import ru.bisoft.rent.ejb.dao.TUserEJB;
import ru.bisoft.rent.model.TUser;

public class LoginBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	@EJB
	TUserEJB tUserEJB;

	private TUser tUser;

	public TUser gettUser() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		if (externalContext.getUserPrincipal() == null)
			this.tUser = null;
		else if (this.tUser == null)
			this.tUser = tUserEJB.find(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()).get(0);
		return this.tUser;
	}

	public void settUser(TUser tUser) {
		this.tUser = tUser;
	}

	public void login() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

		try {
			request.login(username, password);
			tUser = gettUser();
			externalContext.getSessionMap().put("user", tUser);
			externalContext.redirect(externalContext.getRequestContextPath() + "/faces/index.xhtml");
		} catch (ServletException e) {
			context.addMessage(null, new FacesMessage("Unknown login"));
		}
	}

	public void logout() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.invalidateSession();
		externalContext.redirect(externalContext.getRequestContextPath() + "/faces/login.xhtml");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
