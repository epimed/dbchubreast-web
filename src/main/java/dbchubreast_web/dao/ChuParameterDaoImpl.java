/**
 * EpiMed - Information system for bioinformatics developments in the field of epiOmGenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * GNU OmGeneRAL PUBLIC LICENSE
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */

package dbchubreast_web.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuParameter;

@Transactional
@Repository
public class ChuParameterDaoImpl extends BaseDao implements ChuParameterDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuParameter find(Integer idParameter) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuParameter> criteria = builder.createQuery(ChuParameter.class);
			Root<ChuParameter> root = criteria.from(ChuParameter.class);
			criteria.select(root).where(builder.equal(root.get("idParameter"), idParameter));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	
	}
	
	/** ================================================= */

	public ChuParameter find(String nom) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuParameter> criteria = builder.createQuery(ChuParameter.class);
			Root<ChuParameter> root = criteria.from(ChuParameter.class);
			criteria.select(root).where(builder.equal(root.get("nom"), nom));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	
	}

	/** ================================================= */

	public List<ChuParameter> list() {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuParameter> criteria = builder.createQuery(ChuParameter.class);
		Root<ChuParameter> root = criteria.from(ChuParameter.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idParameter")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */
	
	public void save(ChuParameter parameter) {
		sessionFactory.getCurrentSession().save(parameter);
	}
	
	/** ================================================= */
	
	public void update(ChuParameter parameter) {
		sessionFactory.getCurrentSession().update(parameter);
	}
	
	/** ================================================= */
	
	public void saveOrUpdate(ChuParameter parameter) {
		sessionFactory.getCurrentSession().saveOrUpdate(parameter);
	}
	
	/** ================================================= */
	
	public void delete(ChuParameter parameter) {
		sessionFactory.getCurrentSession().delete(parameter);
	}
	
	/** ================================================= */

}
