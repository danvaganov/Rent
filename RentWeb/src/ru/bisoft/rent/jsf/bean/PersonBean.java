package ru.bisoft.rent.jsf.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ru.bisoft.rent.ejb.dao.PersonEJB;
import ru.bisoft.rent.model.Person;
import ru.bisoft.rent.model.PersonOrganization;

public class PersonBean extends  LazyDataModel<Person> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	PersonEJB personEJB;
	
	LoginBean loginBean;
	
	Person selection;
	
	public PersonBean() {
		super();
	}

	public List<Person> complete(String query)
	{
		Map<String, Object> filters = new HashMap<String, Object> ();
		filters.put("surnamePerson", query);
		return personEJB.find(loginBean.gettUser().getEmployee().getOrganization(), 0, 10, filters);
	}
	
	@Override
	public List<Person> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return personEJB.find(loginBean.gettUser().getEmployee().getOrganization(), first, pageSize, filters);
	}
	
	@Override
	public int getRowCount() {
		return personEJB.getCount(loginBean.gettUser().getEmployee().getOrganization()).intValue();
	}
	
	@Override
	public Object getRowKey(Person object) {
		return Integer.valueOf(object.getKeyPerson());
	}
	
	@Override
	public Person getRowData(String rowKey) {
		return personEJB.findById(Integer.valueOf(rowKey));
	}

	public Person getSelection() {
		return selection;
	}

	public void setSelection(Person selection) {
		this.selection = selection;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	public void updatePerson()
	{
		personEJB.update(selection);
	}
	
	public void deletePerson(Person person)
	{
		personEJB.delete(person.getKeyPerson());
	}
	
	public void prepareInsert()
	{
		selection = new Person();
		PersonOrganization personOrganization = new PersonOrganization();
		personOrganization.setPerson(selection);
		personOrganization.setOrganization(loginBean.gettUser().getEmployee().getOrganization());
		selection.getPersonOrganizations().add(personOrganization);
	}
}
