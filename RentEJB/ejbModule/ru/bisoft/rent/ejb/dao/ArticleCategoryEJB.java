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

import ru.bisoft.rent.model.ArticleCategory;

/**
 * Session Bean implementation class ArticleCategoryEJB
 */
@Stateless
@LocalBean
public class ArticleCategoryEJB {
	@PersistenceContext
	EntityManager em;
    /**
     * Default constructor. 
     */
    public ArticleCategoryEJB() {
        // TODO Auto-generated constructor stub
    }

    /*
     * find paging from start index, page size
     */
	public List<ArticleCategory> find() {
		return em.createNamedQuery("ArticleCategory.findAll", ArticleCategory.class).getResultList();
	}
	
	public List<ArticleCategory> find(Integer startIndex, Integer pageSize) {
		return em.createNamedQuery("ArticleCategory.findAll", ArticleCategory.class).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}

	public List<ArticleCategory> find(Integer startIndex, Integer pageSize, Map<String, Object> filters)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		CriteriaQuery<ArticleCategory> query = cb.createQuery(ArticleCategory.class);
		Root<ArticleCategory> root = query.from(ArticleCategory.class);
		query.select(root);
		query.orderBy(cb.asc(root.get("keyArticleCategory")));
		
		for (Map.Entry<String, Object> entry: filters.entrySet())
			predicateList.add(cb.like(cb.lower(root.<String>get(entry.getKey())), entry.getValue().toString() + "%"));
		query.where(cb.and(predicateList.toArray(new Predicate[predicateList.size()])));
		
		return em.createQuery(query).setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
	}
	
	public List<ArticleCategory> find (String emailArticleCategory)
	{
		return em.createNamedQuery("ArticleCategory.findByEmail", ArticleCategory.class).setParameter("emailArticleCategory", emailArticleCategory).getResultList();
	}
	
	//@Override
	public ArticleCategory findById(Integer ID) {
		return em.find(ArticleCategory.class, ID);
	}

	//@Override
	public List<ArticleCategory> findByName(String name) {
		return null;
	}

	//@Override
	public void insert(ArticleCategory arg0) {
		em.persist(arg0);
	}

	//@Override
	public void update(ArticleCategory arg0) {
		em.merge(arg0);
	}

	//@Override
	public void delete(ArticleCategory arg0) {
		em.remove(arg0);
	}

	//@Override
	public void delete(Integer ID) {
		em.remove(em.find(ArticleCategory.class, ID));
	}

	//@Override
	public Long getCount() {
		return (Long) em.createNamedQuery("ArticleCategory.getCount").getSingleResult();
	}
}
