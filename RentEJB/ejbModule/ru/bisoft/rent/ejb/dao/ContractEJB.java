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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ru.bisoft.rent.model.Contract;
import ru.bisoft.rent.model.Organization;

/**
 * Session Bean implementation class ContractEJB
 */
@Stateless
@LocalBean
public class ContractEJB {
	@PersistenceContext
	EntityManager em;
    /**
     * Default constructor. 
     */
    public ContractEJB() {
    }

    /*
     * find paging from start index, page size
     */
	public List<Contract> find() {
		return em.createNamedQuery("Contract.findAll", Contract.class).getResultList();
	}
	
	public List<Contract> find(Integer startIndex, Integer pageSize) {
		return em.createNamedQuery("Contract.findAll", Contract.class).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}

	public List<Contract> find(Organization organization, Integer startIndex, Integer pageSize, Map<String, Object> filters)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		CriteriaQuery<Contract> query = cb.createQuery(Contract.class);
		Root<Contract> root = query.from(Contract.class);
		query.select(root);
		query.orderBy(cb.desc(root.get("keyContract")));
		predicateList.add(cb.equal(root.<String>get("organization"), organization));
		
		for (Map.Entry<String, Object> entry: filters.entrySet())
		{
			if(entry.getKey().compareTo("stateContract") == 0)
				predicateList.add(cb.equal(root.<String>get(entry.getKey()), entry.getValue()));
			else
				predicateList.add(cb.like(cb.lower(root.<String>get(entry.getKey())), entry.getValue().toString() + "%"));
		}
		query.where(cb.and(predicateList.toArray(new Predicate[predicateList.size()])));
		
		return em.createQuery(query).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}
	
	public List<Contract> find (String emailContract)
	{
		return em.createNamedQuery("Contract.findByEmail", Contract.class).setParameter("emailContract", emailContract).getResultList();
	}
	
	public Integer getNumberContract(Organization organization)
	{
		return (Integer) em.createNamedQuery("Contract.getNumberContract").setParameter("KEY_ORGANIZATION", organization.getKeyOrganization()).getSingleResult();
	}
	
	//@Override
	public Contract findById(Integer ID) {
		return em.find(Contract.class, ID);
	}
	
	public void updateOutOfDate(Organization organization) {
		em.createQuery("UPDATE Contract c SET c.stateContract = ru.bisoft.rent.model.Contract.ContractState.OUT_OF_DATE WHERE c.endContract < CURRENT_DATE AND c.stateContract = ru.bisoft.rent.model.Contract.ContractState.PROCESS AND c.organization = :organization").setParameter("organization", organization).executeUpdate();
	}

	//@Override
	public List<Contract> findByName(String name) {
		return null;
	}

	//@Override
	public void insert(Contract arg0) {
		em.persist(arg0);
	}

	//@Override
	public void update(Contract arg0) {
		em.merge(arg0);
	}

	//@Override
	public void delete(Contract arg0) {
		em.remove(arg0);
	}

	//@Override
	public void delete(Integer ID) {
		em.remove(em.find(Contract.class, ID));
	}

	//@Override
	public Long getCount() {
		return (Long) em.createNamedQuery("Contract.getCount").getSingleResult();
	}
	
	public Long getCountByOrganization(Organization organization) {
		return (Long) em.createNamedQuery("Contract.getCountByOrganization").setParameter("organization", organization).getSingleResult();
	}
}
