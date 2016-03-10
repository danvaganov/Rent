package ru.bisoft.rent.ejb.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.bisoft.rent.model.Organization;

/**
 * Session Bean implementation class OrganizationEJB
 */
@Stateless
@LocalBean
public class OrganizationEJB {
	@PersistenceContext
	EntityManager em;
    /**
     * Default constructor. 
     */
    public OrganizationEJB() {
        // TODO Auto-generated constructor stub
    }

	//@Override
	public Organization findById(Integer ID) {
		return em.find(Organization.class, ID);
	}

	//@Override
	public List<Organization> findByName(String name) {
		return null;
	}

	//@Override
	public void insert(Organization arg0) {
		em.persist(arg0);
	}

	//@Override
	public void update(Organization arg0) {
		em.merge(arg0);
	}

	//@Override
	public void delete(Organization arg0) {
		em.remove(arg0);
	}

	//@Override
	public void delete(Integer ID) {
		em.remove(em.find(Organization.class, ID));
	}
}
