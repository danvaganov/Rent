package ru.bisoft.rent.jsf.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import ru.bisoft.rent.ejb.dao.PersonEJB;
import ru.bisoft.rent.model.Person;

public class PersonConverter implements Converter{

	@EJB
	PersonEJB personEJB;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return personEJB.findById(Integer.valueOf(arg2));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return String.valueOf(((Person) arg2).getKeyPerson());
	}
}
