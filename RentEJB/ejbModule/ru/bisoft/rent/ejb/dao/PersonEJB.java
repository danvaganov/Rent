package ru.bisoft.rent.ejb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ru.bisoft.rent.model.Organization;
import ru.bisoft.rent.model.Passport;
import ru.bisoft.rent.model.Person;
import ru.bisoft.rent.model.PersonOrganization;

/**
 * Session Bean implementation class PersonEJB
 */
@Stateless
@LocalBean
public class PersonEJB {
	@PersistenceContext
	EntityManager em;
    /**
     * Default constructor. 
     */
    public PersonEJB() {
    }
    
    //@Override
	public List<Person> findAll() {
		return em.createNamedQuery("Person.findAll", Person.class).getResultList();
	}

	//@Override
	public List<Person> find(Integer startIndex, Integer pageSize) {
		return em.createNamedQuery("Person.findAll", Person.class).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}
	
	public List<Person> findByEmail (String emailPerson)
	{
		return em.createNamedQuery("Person.findByEmail", Person.class).setParameter("emailPerson", emailPerson).getResultList();
	}
	
	public Person findByPassport (Passport passport)
	{
		return em.createNamedQuery("Person.findByPassport", Person.class).setParameter("serialPassport", passport.getSerialPassport()).setParameter("numberPassport", passport.getNumberPassport()).setParameter("issueDatePassport", passport.getIssueDatePassport()).getSingleResult();
	}
	//@Override
	public Person findById(Integer ID) {
		return em.find(Person.class, ID);
	}

	//@Override
	public List<Person> find(Organization organization, Integer startIndex, Integer pageSize) {
		return em.createNamedQuery("Person.findByOrganization", Person.class).setParameter("organization", organization).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}

	public List<Person> find(Organization organization, Integer startIndex, Integer pageSize, Map<String, Object> filters)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		CriteriaQuery<Person> query = cb.createQuery(Person.class);
		Root<Person> root = query.from(Person.class);
		Join<Person, PersonOrganization> join = root.join("personOrganizations");
		query.select(root);
		query.orderBy(cb.asc(root.get("keyPerson")));
		predicateList.add(cb.equal(join.<String>get("organization"), organization));
		
		for (Map.Entry<String, Object> entry: filters.entrySet())
			predicateList.add(cb.like(cb.lower(root.<String>get(entry.getKey())), entry.getValue().toString() + "%"));
		query.where(cb.and(predicateList.toArray(new Predicate[predicateList.size()])));
		
		return em.createQuery(query).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}

	//@Override
	public void insert(Person arg0) {
		em.persist(arg0);
	}

	//@Override
	public void update(Person arg0) {
		em.merge(arg0);
	}

	//@Override
	public void delete(Person arg0) {
		em.remove(arg0);
	}

	//@Override
	public void delete(Integer ID) {
		em.remove(em.find(Person.class, ID));
	}

	//@Override
	public Long getCount(Organization organization) {
		return (Long) em.createNamedQuery("Person.getCountByOrganization").setParameter("organization", organization).getSingleResult();
	}
}
