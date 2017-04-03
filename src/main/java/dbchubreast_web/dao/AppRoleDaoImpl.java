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

import dbchubreast_web.entity.AppRole;


@Transactional
@Repository
public class AppRoleDaoImpl extends BaseDao implements AppRoleDao {


	@Autowired
	private SessionFactory sessionFactory;


	/** ================================================= */

	public AppRole findById(String idRole) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<AppRole> criteria = builder.createQuery(AppRole.class);
			Root<AppRole> root = criteria.from(AppRole.class);
			criteria.select(root).where(builder.equal(root.get("idRole"), idRole));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();

		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public List<AppRole> list(){

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<AppRole> criteria = builder.createQuery(AppRole.class);
		Root<AppRole> root = criteria.from(AppRole.class);
		criteria.select(root);
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		
	}

	/** ================================================= */
}
