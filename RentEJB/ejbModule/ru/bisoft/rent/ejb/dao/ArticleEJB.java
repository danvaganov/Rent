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

import ru.bisoft.rent.model.Article;
import ru.bisoft.rent.model.Article.ArticleState;
import ru.bisoft.rent.model.ArticleCategory;
import ru.bisoft.rent.model.Organization;

/**
 * Session Bean implementation class ArticleEJB
 */
@Stateless
@LocalBean
public class ArticleEJB {
	@PersistenceContext
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ArticleEJB() {
        // TODO Auto-generated constructor stub
    }
    /*
     * find paging from start index, page size
     */
	public List<Article> find() {
		return em.createNamedQuery("Article.findAll", Article.class).getResultList();
	}
	
	public List<Article> find(Organization organization, Integer startIndex, Integer pageSize) {
		return em.createNamedQuery("Article.findByOrganization", Article.class).setParameter("organization", organization).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}

	public List<Article> find (Organization organization, ArticleCategory articleCategory, Integer startIndex, Integer pageSize)
	{
		return em.createNamedQuery("Article.findByOrganizationCategory", Article.class).setParameter("organization", organization).setParameter("articleCategory", articleCategory).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}
	
	public List<Article> findInStock (Organization organization, Integer startIndex, Integer pageSize)
	{
		return em.createNamedQuery("Article.findByOrganizationState", Article.class).setParameter("organization", organization).setParameter("stateArticle", ArticleState.IN_STOCK).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}
	
	public Long getCountInStock(Organization organization) {
		return (Long) em.createNamedQuery("Article.getCountByOrganizationState").setParameter("organization", organization).setParameter("stateArticle", ArticleState.IN_STOCK).getSingleResult();
	}
	
	public List<Article> findInRent (Organization organization, Integer startIndex, Integer pageSize)
	{
		return em.createNamedQuery("Article.findByOrganizationState", Article.class).setParameter("organization", organization).setParameter("stateArticle", ArticleState.IN_RENT).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}
	
	public Long getCountInRent(Organization organization) {
		return (Long) em.createNamedQuery("Article.getCountByOrganizationState").setParameter("organization", organization).setParameter("stateArticle", ArticleState.IN_RENT).getSingleResult();
	}

	public Long getCountWriteOff(Organization organization) {
		return (Long) em.createNamedQuery("Article.getCountByOrganizationState").setParameter("organization", organization).setParameter("stateArticle", ArticleState.WRITE_OFF).getSingleResult();
	}
	
	public Article findById(Integer ID) {
		return em.find(Article.class, ID);
	}
	
	public List<Article> find(Organization organization, ArticleState articleState, Integer startIndex, Integer pageSize, Map<String, Object> filters)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		CriteriaQuery<Article> query = cb.createQuery(Article.class);
		Root<Article> root = query.from(Article.class);
		query.select(root);
		query.orderBy(cb.asc(root.get("keyArticle")));
		predicateList.add(cb.equal(root.<String>get("organization"), organization));
		predicateList.add(cb.equal(root.<String>get("stateArticle"), articleState));
		
		for (Map.Entry<String, Object> entry: filters.entrySet())
			predicateList.add(cb.like(cb.lower(root.<String>get(entry.getKey())), entry.getValue().toString() + "%"));
		query.where(cb.and(predicateList.toArray(new Predicate[predicateList.size()])));
		
		return em.createQuery(query).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}


	//@Override
	public void insert(Article arg0) {
		em.persist(arg0);
	}

	//@Override
	public void update(Article arg0) {
		em.merge(arg0);
	}

	//@Override
	public void delete(Article arg0) {
		em.remove(arg0);
	}

	//@Override
	public void delete(Integer ID) {
		em.remove(em.find(Article.class, ID));
	}

	//@Override
	public Long getCountByOrganization(Organization organization) {
		return (Long) em.createNamedQuery("Article.getCountByOrganization").setParameter("organization", organization).getSingleResult();
	}
}
