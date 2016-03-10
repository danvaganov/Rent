package ru.bisoft.rent.ejb.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.bisoft.rent.model.ContractArticle;

/**
 * Session Bean implementation class ContractArticleEJB
 */
@Stateless
@LocalBean
public class ContractArticleEJB {
	@PersistenceContext
	EntityManager em;
    /**
     * Default constructor. 
     */
    public ContractArticleEJB() {
        // TODO Auto-generated constructor stub
    }

	//@Override
	public void insert(ContractArticle arg0) {
		em.persist(arg0);
	}

	//@Override
	public void update(ContractArticle arg0) {
		em.merge(arg0);
	}

	//@Override
	public void delete(ContractArticle arg0) {
		em.remove(arg0);
	}

	//@Override
	public void delete(Integer ID) {
		em.remove(em.find(ContractArticle.class, ID));
	}
}
