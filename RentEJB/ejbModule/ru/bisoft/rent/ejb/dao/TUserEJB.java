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

import ru.bisoft.rent.model.TUser;

/**
 * Session Bean implementation class TUserEJB
 */
@Stateless
@LocalBean
public class TUserEJB {
	@PersistenceContext
	EntityManager em;
    /**
     * Default constructor. 
     */
    public TUserEJB() {
        // TODO Auto-generated constructor stub
    }

	//@Override
	public List<TUser> find(String userIDTUser) {
		return em.createNamedQuery("TUser.findByID", TUser.class).setParameter("userIDTUser", userIDTUser).getResultList();
	}
	
	public List<TUser> find(Integer startIndex, Integer pageSize, Map<String, Object> filters)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		CriteriaQuery<TUser> query = cb.createQuery(TUser.class);
		Root<TUser> root = query.from(TUser.class);
//		Join<TUser, Employee> join = root.join("employee");
		query.select(root);
		query.orderBy(cb.asc(root.get("userIDTUser")));
		
		for (Map.Entry<String, Object> entry: filters.entrySet())
			predicateList.add(cb.like(cb.lower(root.<String>get(entry.getKey())), entry.getValue().toString() + "%"));
		query.where(cb.and(predicateList.toArray(new Predicate[predicateList.size()])));
		
		return em.createQuery(query).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}
	
	public Long getCount() {
		return (Long) em.createNamedQuery("TUser.getCount").getSingleResult();
	}
	
	public TUser findById(String ID) {
		return em.find(TUser.class, ID);
	}
	
	//@Override
	public void insert(TUser arg0) {
		em.persist(arg0);
	}

	//@Override
	public void update(TUser arg0) {
		em.merge(arg0);
	}

	//@Override
	public void delete(TUser arg0) {
		em.remove(arg0);
	}
	
	public void delete(String ID) {
		em.remove(em.find(TUser.class, ID));
	}
}
