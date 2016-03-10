package ru.bisoft.rent.jsf.bean;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;



public class MainBean {

	private String content;
	
	public String getContent() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		if (externalContext.getUserPrincipal() == null)
			externalContext.redirect(externalContext.getRequestContextPath() + "/faces/login.xhtml");
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
