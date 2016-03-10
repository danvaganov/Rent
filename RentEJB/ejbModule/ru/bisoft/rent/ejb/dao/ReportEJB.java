package ru.bisoft.rent.ejb.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class ReportEJB
 */
@Stateless
@LocalBean
public class ReportEJB {
	@PersistenceContext
	EntityManager em;
    /**
     * Default constructor. 
     */
    public ReportEJB() {
        // TODO Auto-generated constructor stub
    }

	@SuppressWarnings("unchecked")
	public List<Object[]> getCreatedContract ()
    {
    	return em.createNativeQuery("select to_char(create_contract, 'DD'), COUNT(*) from contract WHERE create_contract between '2015-01-08' and '2016-01-01' group by 1").getResultList();
    }
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getClosedContract ()
    {
    	return em.createNativeQuery("select to_char(create_contract, 'DD'), COUNT(*) from contract WHERE create_contract between '2015-01-08' and '2016-01-01' and state_contract = 2 group by 1").getResultList();
    }
}
